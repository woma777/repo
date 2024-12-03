package com.saas.leave.mapper;

import com.saas.leave.dto.request.LeaveTypeRequest;

import com.saas.leave.dto.response.LeaveTypeResponse;

import com.saas.leave.model.LeaveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LeaveTypeMapper {

    /**
     * Maps a LeaveTypeRequestDTO to a LeaveType entity.
     *
     * @param request the LeaveTypeRequestDTO to map
     * @param tenantId the tenant ID to set
     * @return the mapped LeaveType entity
     */
    public LeaveType toEntity(LeaveTypeRequest request, UUID tenantId) {
        LeaveType leaveType = new LeaveType();
        leaveType.setLeaveTypeName(request.getLeaveTypeName());
        leaveType.setTenantId(tenantId);

        return leaveType;
    }

    /**
     * Maps a LeaveType entity to a LeaveTypeResponseDTO.
     *
     * @param entity the LeaveType entity to map
     * @return the mapped LeaveTypeResponseDTO
     */
    public LeaveTypeResponse toResponseDTO(LeaveType entity) {
        LeaveTypeResponse response = new LeaveTypeResponse();
        response.setId(entity.getId());
        response.setLeaveTypeName(entity.getLeaveTypeName());
        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing LeaveType entity with data from a LeaveTypeRequestDTO.
     *
     * @param leaveType the existing LeaveType entity to update
     * @param request the LeaveTypeRequestDTO containing updated data
     */
    public void updateLeaveType(LeaveType leaveType, LeaveTypeRequest request) {
        if (request.getLeaveTypeName() != null) {
            leaveType.setLeaveTypeName(request.getLeaveTypeName());
        }

    }
}
