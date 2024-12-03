package com.saas.leave.dto.response;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Data
@NoArgsConstructor
public class LeaveBalanceResponse {
    private UUID id;
    private UUID employeeId;
    private UUID budgetYearId;
    private int totalLeaveDays;
    private int remainingDays;
    private UUID tenantId;

    // Constructor
    public LeaveBalanceResponse(UUID id, UUID employeeId, UUID budgetYearId, int totalLeaveDays, int remainingDays, UUID tenantId) {
        this.id = id;
        this.employeeId = employeeId;
        this.budgetYearId = budgetYearId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingDays = remainingDays;
        this.tenantId = tenantId;
    }



}
