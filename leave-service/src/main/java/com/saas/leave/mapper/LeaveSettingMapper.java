package com.saas.leave.mapper;

import com.saas.leave.dto.request.LeaveSettingRequest;
import com.saas.leave.dto.response.LeaveSettingResponse;
import com.saas.leave.model.LeaveSetting;
import com.saas.leave.model.LeaveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveSettingMapper {

    /**
     * Maps a LeaveSettingRequest to a LeaveSetting entity.
     *
     * @param request the LeaveSettingRequest to map
     * @return the mapped LeaveSetting entity
     */
    public LeaveSetting toLeaveSetting(LeaveSettingRequest request) {
        LeaveSetting leaveSetting = new LeaveSetting();
        // Map request fields to entity fields
        leaveSetting.setGender(request.getGender());
        leaveSetting.setEmploymentType(request.getEmploymentType());
        leaveSetting.setMinimumDays(request.getMinimumDays());
        leaveSetting.setMaximumDays(request.getMaximumDays());
        leaveSetting.setRemark(request.getRemark());
        leaveSetting.setToBalance(request.getToBalance());
        leaveSetting.setEscapeSunday(request.getEscapeSunday());
        leaveSetting.setEscapeSaturday(request.getEscapeSaturday());
        leaveSetting.setEscapeHoliday(request.getEscapeHoliday());

        // Map LeaveType
        LeaveType leaveType = new LeaveType();
        leaveType.setId(request.getLeaveTypeId());
        leaveSetting.setLeaveType(leaveType);

        // Uncomment if TenantId needs to be set
        // leaveSetting.setTenantId(request.getTenantId());

        return leaveSetting;
    }

    /**
     * Maps a LeaveSetting entity to a LeaveSettingResponse.
     *
     * @param entity the LeaveSetting entity to map
     * @return the mapped LeaveSettingResponse
     */
    public LeaveSettingResponse toLeaveSettingResponseDTO(LeaveSetting entity) {
        LeaveSettingResponse response = new LeaveSettingResponse();
        // Map entity fields to response fields
        response.setId(entity.getId());
        response.setGender(entity.getGender());
        response.setEmploymentType(entity.getEmploymentType());
        response.setMinimumDays(entity.getMinimumDays());
        response.setMaximumDays(entity.getMaximumDays());
        response.setRemark(entity.getRemark());
        response.setToBalance(entity.getToBalance());
        response.setEscapeSunday(entity.getEscapeSunday());
        response.setEscapeSaturday(entity.getEscapeSaturday());
        response.setEscapeHoliday(entity.getEscapeHoliday());

        // Safely map LeaveTypeId
        if (entity.getLeaveType() != null) {
            response.setLeaveTypeId(entity.getLeaveType().getId());
        }

        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing LeaveSetting entity with data from a LeaveSettingRequest.
     *
     * @param leaveSetting the existing LeaveSetting entity to update
     * @param request      the LeaveSettingRequest containing updated data
     */
    public void updateLeaveSetting(LeaveSetting leaveSetting, LeaveSettingRequest request) {
        // Update entity fields with data from request
        leaveSetting.setGender(request.getGender());
        leaveSetting.setEmploymentType(request.getEmploymentType());
        leaveSetting.setMinimumDays(request.getMinimumDays());
        leaveSetting.setMaximumDays(request.getMaximumDays());
        leaveSetting.setRemark(request.getRemark());
        leaveSetting.setToBalance(request.getToBalance());
        leaveSetting.setEscapeSunday(request.getEscapeSunday());
        leaveSetting.setEscapeSaturday(request.getEscapeSaturday());
        leaveSetting.setEscapeHoliday(request.getEscapeHoliday());

        // Update LeaveType if necessary
        if (leaveSetting.getLeaveType() == null) {
            leaveSetting.setLeaveType(new LeaveType());
        }
        leaveSetting.getLeaveType().setId(request.getLeaveTypeId());

        // Uncomment this if TenantId should be updated
        // leaveSetting.setTenantId(request.getTenantId());
    }
}
