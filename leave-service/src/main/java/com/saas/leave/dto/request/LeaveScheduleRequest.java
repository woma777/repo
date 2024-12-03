package com.saas.leave.dto.request;

import com.saas.leave.enums.LeaveMonth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveScheduleRequest {
    private UUID employeeId;
    private LeaveMonth leaveMonth;
    private String description;

}
