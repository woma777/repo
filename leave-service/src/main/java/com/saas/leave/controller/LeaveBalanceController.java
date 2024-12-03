package com.saas.leave.controller;

import com.saas.leave.dto.response.LeaveBalanceResponse;
import com.saas.leave.model.LeaveBalanceHistory;
import com.saas.leave.service.LeaveBalanceService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-balance/{tenantId}")
@AllArgsConstructor
@Tag(name = "Leave Balance")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;
    private final PermissionEvaluator permissionEvaluator;

    @GetMapping("/leave_history/{employeeId}")
    public ResponseEntity<List<LeaveBalanceHistory>> getAllLeaveBalancesForEmployee(
            @PathVariable UUID employeeId,
            @PathVariable UUID tenantId) {

        permissionEvaluator.getEmployeeLeaveBalancePermission(tenantId);

        List<LeaveBalanceHistory> leaveBalances = leaveBalanceService
                .getAllLeaveBalancesForEmployee(employeeId, tenantId);

        if (leaveBalances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 204 No Content if no balances found
        }

        return ResponseEntity.ok(leaveBalances); // Return 200 OK with the leave balances
    }
    @GetMapping("/calculate/{budgetYearId}/{employeeId}")
    public ResponseEntity<LeaveBalanceResponse> getLeaveBalanceForEmployee(
            @PathVariable UUID budgetYearId, @PathVariable UUID employeeId, @PathVariable UUID tenantId) {

        permissionEvaluator.getAllEmployeeLeaveBalanceHistoriesPermission(tenantId);

        // Call the service to calculate the leave balance for the employee
        LeaveBalanceResponse leaveBalanceResponse = leaveBalanceService
                .calculateLeaveBalanceForEmployee(tenantId, employeeId, budgetYearId);
        return ResponseEntity.ok(leaveBalanceResponse);


    }
}

