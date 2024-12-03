package com.saas.leave.dto.request;

import com.saas.leave.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestRequest {

    private UUID employeeId;
    private int numberOfDays;
    private LocalDate leaveStart;
    private Day day;
    private String description;
    private UUID leaveTypeId;
    private UUID budgetYearId;


}
