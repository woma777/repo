package com.saas.leave.dto.response;

import com.saas.leave.enums.Day;
import com.saas.leave.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestResponse {
    private UUID id;
    private UUID employeeId;
    private int numberOfDays;
    private LocalDate leaveStart;
    private Day day;
    private LocalDate returnDate;
    private String description;
    private UUID leaveTypeId;
    private UUID budgetYearId;
    private UUID tenantId;
    private int numberOfApprovedDays;
    private Status departmentDecision;
    private Status HrDecision;
    private String comment;
}
