package com.saas.leave.mapper;

import com.saas.leave.client.EmployeeServiceClient;
import com.saas.leave.dto.request.LeaveScheduleRequest;
import com.saas.leave.dto.response.LeaveScheduleResponse;
import com.saas.leave.model.LeaveSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveScheduleMapper {

    private final EmployeeServiceClient employeeServiceClient;

    /**
     * Converts a LeaveScheduleRequest DTO to a LeaveSchedule entity.
     *
     * @param request the LeaveScheduleRequest DTO to convert
     * @return the converted LeaveSchedule entity
     */
    public LeaveSchedule toLeaveSchedule(LeaveScheduleRequest request) {
        if (request == null) {
            return null;
        }

        LeaveSchedule leaveSchedule = new LeaveSchedule();
        leaveSchedule.setEmployeeId(request.getEmployeeId()); // Assuming employeeId is already a UUID
        leaveSchedule.setLeaveMonth(request.getLeaveMonth());
        leaveSchedule.setDescription(request.getDescription());
        // If tenantId is required, uncomment the line below and set tenantId
        // leaveSchedule.setTenantId(request.getTenantId());

        return leaveSchedule;
    }

    /**
     * Maps a LeaveSchedule entity to a LeaveScheduleResponse DTO.
     *
     * @param entity the LeaveSchedule entity to map
     * @return the mapped LeaveScheduleResponse DTO
     */
    public LeaveScheduleResponse toLeaveScheduleResponse(LeaveSchedule entity) {
        if (entity == null) {
            return null;
        }

        LeaveScheduleResponse response = new LeaveScheduleResponse();
        response.setId(entity.getId());
        response.setEmployeeId(entity.getEmployeeId()); // Assuming employeeId is UUID in LeaveSchedule
        response.setLeaveMonth(entity.getLeaveMonth());
        response.setDescription(entity.getDescription());
        response.setTenantId(entity.getTenantId());

        return response;
    }

    /**
     * Updates an existing LeaveSchedule entity with data from a LeaveScheduleRequest.
     *
     * @param leaveSchedule the existing LeaveSchedule entity to update
     * @param request       the LeaveScheduleRequest containing updated data
     */
    public void updateLeaveSchedule(LeaveSchedule leaveSchedule, LeaveScheduleRequest request) {
        if (leaveSchedule == null || request == null) {
            return;
        }

        if (request.getEmployeeId() != null) {
            leaveSchedule.setEmployeeId(request.getEmployeeId()); // Assuming employeeId is UUID
        }
        if (request.getLeaveMonth() != null) {
            leaveSchedule.setLeaveMonth(request.getLeaveMonth());
        }
        if (request.getDescription() != null) {
            leaveSchedule.setDescription(request.getDescription());
        }
        // If tenantId is required, uncomment the line below and set tenantId
        // leaveSchedule.setTenantId(request.getTenantId());
    }
}
