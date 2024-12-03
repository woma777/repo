package com.saas.leave.service;

import com.saas.leave.client.EmployeeServiceClient;
import com.saas.leave.client.OrganizationServiceClient;
import com.saas.leave.dto.EmployeeDto;
import com.saas.leave.dto.TenantDto;
import com.saas.leave.dto.response.LeaveBalanceResponse;
import com.saas.leave.model.*;
import com.saas.leave.enums.Status;
import com.saas.leave.exception.ResourceNotFoundException;
import com.saas.leave.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveSettingRepository leaveSettingRepository;
    private final EmployeeServiceClient employeeServiceClient;
    private final BudgetYearRepository budgetYearRepository;
    private final LeaveBalanceHistoryRepository leaveBalanceHistoryRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final OrganizationServiceClient organizationServiceClient;

    private static final int BASE_LEAVE_DAYS = 20;
    private static final int MIN_EXPERIENCE_YEARS = 1;
    private static final Logger logger = LoggerFactory.getLogger(LeaveBalanceService.class);

    @Transactional
    public LeaveBalanceResponse calculateLeaveBalanceForEmployee(UUID tenantId, UUID employeeId, UUID budgetYearId) {
        TenantDto tenantDto = organizationServiceClient.getTenantById(tenantId);
        logger.info("Calculating leave balance for employee ID: {}, Budget Year ID: {}, Tenant ID: {}", employeeId, budgetYearId, tenantId);

        // Retrieve the budget year
        BudgetYear budgetYear = budgetYearRepository.findById(budgetYearId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget year not found for ID: " + budgetYearId));

        // Retrieve the leave setting for the tenant
        List<LeaveSetting> leaveSettings = leaveSettingRepository.findByTenantId(tenantId);
        int maxDays = leaveSettings.stream()
                .max(Comparator.comparing(LeaveSetting::getId))  // Assuming a sorted list by ID
                .map(LeaveSetting::getMaximumDays)
                .orElseThrow(() -> new ResourceNotFoundException("Leave setting must be set for tenant ID: " + tenantId));

        // Retrieve the employee details
        EmployeeDto employee = employeeServiceClient.getEmployeeById(tenantId, employeeId);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found for ID: " + employeeId);
        }

        logger.info("Employee ID: {}, Hired Date: {}", employee.getId(), employee.getHiredDate());

        // Calculate the total and remaining leave days
        int totalLeaveDays = calculateTotalLeaveDays(employeeId);
        int remainingLeaveDays = calculateRemainingLeaveDays(employee, maxDays, totalLeaveDays);

        logger.info("Total leave days for employee ID {}: {}", employeeId, totalLeaveDays);
        logger.info("Remaining leave days for employee ID {}: {}", employeeId, remainingLeaveDays);

        // Save or update the leave balance
        saveOrUpdateLeaveBalance(employeeId, remainingLeaveDays, totalLeaveDays, tenantId, budgetYearId);

        // Save the current leave balance to history
        saveCurrentLeaveBalanceToHistory(employeeId, totalLeaveDays, remainingLeaveDays, budgetYear.getId(), tenantId);

        // Return the response for this employee
        return new LeaveBalanceResponse(
                employeeId,
                employee.getId(),
                budgetYear.getId(),
                totalLeaveDays,
                remainingLeaveDays,
                tenantId
        );
    }



    private int calculateTotalLeaveDays(UUID employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId).stream()
                .filter(leaveRequest -> leaveRequest.getDepartmentDecision() == Status.APPROVED)
                .max(Comparator.comparing(LeaveRequest::getId))// Assuming there's an APPROVED status
                .map(LeaveRequest::getNumberOfApprovedDays)
                .orElse(0);
    }

    private int calculateRemainingLeaveDays(EmployeeDto employee, int maxDays, int totalLeaveDays) {
        LocalDate startDate = employee.getHiredDate();
        LocalDate currentDate = LocalDate.now();
        Period experiencePeriod = Period.between(startDate, currentDate);

        if (experiencePeriod.getYears() < MIN_EXPERIENCE_YEARS) {
            logger.warn("Employee ID {} does not meet the minimum experience years requirement.", employee.getId());
            return 0; // No leave for employees not meeting experience criteria
        }

        int totalAvailableDays = BASE_LEAVE_DAYS + experiencePeriod.getYears();
        totalAvailableDays = Math.min(totalAvailableDays, maxDays);

        // Check if the employee has used leave in the last two years
        LocalDate twoYearsAgo = currentDate.minusYears(2);
        boolean hasUsedLeaveInLastTwoYears = leaveBalanceHistoryRepository.findByEmployeeId(employee.getId()).stream()
                .anyMatch(history -> history.getCalculationDate().isAfter(twoYearsAgo));

        int remainingLeaveDays = hasUsedLeaveInLastTwoYears ? totalAvailableDays - totalLeaveDays : totalAvailableDays;
        logger.info("Remaining leave days calculated for employee ID {}: {}", employee.getId(), remainingLeaveDays);

        return remainingLeaveDays;
    }

    private void saveOrUpdateLeaveBalance(UUID employeeId, int remainingLeaveDays, int totalLeaveDays, UUID tenantId, UUID budgetYearId) {
        // Fetch existing leave balances for the employee and budget year
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findByEmployeeIdAndBudgetYearId(employeeId, budgetYearId);

        if (leaveBalances.isEmpty()) {
            // Create a new leave balance if none exists
            LeaveBalance newLeaveBalance = createNewLeaveBalance(employeeId, totalLeaveDays, remainingLeaveDays, tenantId, budgetYearId);
            leaveBalanceRepository.save(newLeaveBalance);
            logger.info("Created new leave balance for employee ID: {}", employeeId);
        } else {
            // Handle multiple records by selecting the latest balance using getId()
            LeaveBalance selectedLeaveBalance = resolveLatestLeaveBalance(leaveBalances);
            updateLeaveBalance(selectedLeaveBalance, totalLeaveDays, remainingLeaveDays);
            logger.info("Updated leave balance for employee ID: {}", employeeId);
        }
    }

    private LeaveBalance createNewLeaveBalance(UUID employeeId, int totalLeaveDays, int remainingLeaveDays, UUID tenantId, UUID budgetYearId) {
        // Fetch the BudgetYear object from the repository
        BudgetYear budgetYear = budgetYearRepository.findById(budgetYearId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget year not found for ID: " + budgetYearId));

        // Return a new LeaveBalance instance with the BudgetYear object
        return new LeaveBalance(employeeId, totalLeaveDays, remainingLeaveDays, tenantId, budgetYear);
    }


    private void updateLeaveBalance(LeaveBalance leaveBalance, int totalLeaveDays, int remainingLeaveDays) {
        leaveBalance.setTotalLeaveDays(totalLeaveDays);
        leaveBalance.setRemainingDays(remainingLeaveDays);
        leaveBalanceRepository.save(leaveBalance);
    }

    // Method to resolve and select the latest leave balance using getId()
    private LeaveBalance resolveLatestLeaveBalance(List<LeaveBalance> leaveBalances) {
        return leaveBalances.stream()
                .max(Comparator.comparing(LeaveBalance::getId)) // Select the one with the highest ID
                .orElseThrow(() -> new IllegalStateException("No leave balance found"));
    }

    @Transactional
    public void saveCurrentLeaveBalanceToHistory(UUID employeeId, int totalLeaveDays, int remainingLeaveDays, UUID budgetYearId, UUID tenantId) {
        BudgetYear budgetYear = budgetYearRepository.findById(budgetYearId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget year not found for ID: " + budgetYearId));

        LeaveBalanceHistory leaveBalanceHistory = new LeaveBalanceHistory();
        leaveBalanceHistory.setEmployeeId(employeeId);
        leaveBalanceHistory.setTotalLeaveDays(totalLeaveDays); // Ensure this line sets the value
        leaveBalanceHistory.setRemainingDays(remainingLeaveDays);
        leaveBalanceHistory.setBudgetYear(budgetYear); // If this requires a BudgetYear object, adjust accordingly
        leaveBalanceHistory.setTenantId(tenantId);
        leaveBalanceHistory.setCalculationDate(LocalDate.now());

        leaveBalanceHistoryRepository.save(leaveBalanceHistory);
        logger.info("Saved leave balance history for employee ID: {}", employeeId);
    }

    public List<LeaveBalanceHistory> getAllLeaveBalancesForEmployee(UUID employeeId, UUID tenantId) {
        // Fetch the employee and tenant details using the respective service clients
        EmployeeDto employee = employeeServiceClient.getEmployeeById(tenantId, employeeId);
        TenantDto tenantDto = organizationServiceClient.getTenantById(tenantId);

        // Log information about the fetched employee and tenant
        logger.info("Fetched employee: {}", employee);
        logger.info("Fetched tenant: {}", tenantDto);

        // Fetch all leave balance history records for the given employee ID
        List<LeaveBalanceHistory> balanceResponses = leaveBalanceHistoryRepository.findByEmployeeId(employeeId);

        // You can add any additional processing here if needed
        if (balanceResponses.isEmpty()) {
            logger.warn("No leave balances found for employee ID: {}", employeeId);
        } else {
            logger.info("Found {} leave balances for employee ID: {}", balanceResponses.size(), employeeId);
        }

        // Return the list of leave balances
        return balanceResponses;
    }



}
