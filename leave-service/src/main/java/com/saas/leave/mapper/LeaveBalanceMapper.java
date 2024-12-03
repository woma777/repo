package com.saas.leave.mapper;

import com.saas.leave.dto.request.LeaveBalanceRequest;
import com.saas.leave.dto.response.LeaveBalanceResponse;
import com.saas.leave.model.BudgetYear;
import com.saas.leave.model.LeaveBalance;
import com.saas.leave.repository.BudgetYearRepository;
//import com.insa.leave_service.repository.LeaveBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveBalanceMapper {
    private final BudgetYearRepository budgetYearRepository;
    public LeaveBalance toEntity(LeaveBalanceRequest requestDTO) {
        LeaveBalance leaveBalance = new LeaveBalance();


        // Map BudgetYear if available
        if (requestDTO.getBudgetYearId() != null) {
            BudgetYear budgetYear = budgetYearRepository.findById(requestDTO.getBudgetYearId())
                    .orElseThrow(() -> new RuntimeException("BudgetYear not found with ID: " + requestDTO.getBudgetYearId()));
            leaveBalance.setBudgetYear(budgetYear);
        }

        return leaveBalance;
    }

    public LeaveBalanceResponse mapToDto(LeaveBalance entity) {
        LeaveBalanceResponse response = new LeaveBalanceResponse();
        response.setId(entity.getId());
        response.setEmployeeId(entity.getEmployeeId());
        response.setBudgetYearId(entity.getBudgetYear() != null ? entity.getBudgetYear().getId() : null); // Ensure mapping of budgetYearId
        response.setTotalLeaveDays(entity.getTotalLeaveDays());
        response.setRemainingDays(entity.getRemainingDays());
        response.setTenantId(entity.getTenantId());
        return response;
    }
    public void updateLeaveBalance(LeaveBalance leaveBalance, LeaveBalanceRequest requestDTO) {


        // Map BudgetYear if available
        if (requestDTO.getBudgetYearId() != null) {
            BudgetYear budgetYear = budgetYearRepository.findById(requestDTO.getBudgetYearId())
                    .orElseThrow(() -> new RuntimeException("BudgetYear not found with ID: " + requestDTO.getBudgetYearId()));
            leaveBalance.setBudgetYear(budgetYear);
        }
    }
}
