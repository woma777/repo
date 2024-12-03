package com.saas.leave.dto.response;

import com.saas.leave.enums.LeaveMonth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveScheduleResponse {
    private UUID id;
    private UUID employeeId;
    private LeaveMonth leaveMonth;
    private String description;
    private UUID tenantId;
}
