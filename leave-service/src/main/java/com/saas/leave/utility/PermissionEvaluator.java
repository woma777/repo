package com.saas.leave.utility;

import com.saas.leave.enums.LeaveServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Leave Type Permissions */
    public void addLeaveTypePermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.ADD_LEAVE_TYPE);
    }

    public void getAllLeaveTypesPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_LEAVE_TYPES);
    }

    public void getLeaveTypeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_LEAVE_TYPE_BY_ID);
    }

    public void updateLeaveTypePermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.UPDATE_LEAVE_TYPE);
    }

    public void deleteLeaveTypePermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DELETE_LEAVE_TYPE);
    }

    /* Leave Setting Permissions */
    public void addLeaveSettingPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.ADD_LEAVE_SETTING);
    }

    public void getAllLeaveSettingsPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_LEAVE_SETTINGS);
    }

    public void getLeaveSettingByIdPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_LEAVE_SETTING_BY_ID);
    }

    public void updateLeaveSettingPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.UPDATE_LEAVE_SETTING);
    }

    public void deleteLeaveSettingPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DELETE_LEAVE_SETTING);
    }

    /* Leave Schedule Permissions */
    public void addLeaveSchedulePermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.ADD_LEAVE_SCHEDULE);
    }

    public void getAllLeaveSchedulesPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_LEAVE_SCHEDULES);
    }

    public void getLeaveScheduleByIdPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_LEAVE_SCHEDULE_BY_ID);
    }

    public void updateLeaveSchedulePermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.UPDATE_LEAVE_SCHEDULE);
    }

    public void deleteLeaveSchedulePermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DELETE_LEAVE_SCHEDULE);
    }

    /* Leave Request Permissions */
    public void addLeaveRequestPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.ADD_LEAVE_REQUEST);
    }

    public void getAllLeaveRequestsPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_LEAVE_REQUESTS);
    }

    public void getLeaveRequestByIdPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_LEAVE_REQUEST_BY_ID);
    }

    public void updateLeaveRequestPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.UPDATE_LEAVE_REQUEST);
    }

    public void getLeaveRequestsByStatusPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_LEAVE_REQUESTS_BY_STATUS);
    }

    public void departmentApproveLeaveRequestPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DEPARTMENT_APPROVE_LEAVE_REQUEST);
    }

    public void hrApproveLeaveRequestPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.HR_APPROVE_LEAVE_REQUEST);
    }

    public void deleteLeaveRequestPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DELETE_LEAVE_REQUEST);
    }

    /* Holiday Permissions */
    public void addHolidayPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.ADD_HOLIDAY);
    }

    public void getAllHolidaysPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_HOLIDAYS);
    }

    public void getHolidayByIdPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_HOLIDAY_BY_ID);
    }

    public void updateHolidayPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.UPDATE_HOLIDAY);
    }

    public void deleteHolidayPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DELETE_HOLIDAY);
    }

    /* Holiday Management Permissions */
    public void addHolidayManagementPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.ADD_HOLIDAY_MANAGEMENT);
    }

    public void getAllHolidayManagementsPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_HOLIDAY_MANAGEMENTS);
    }

    public void getHolidayManagementByIdPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_HOLIDAY_MANAGEMENT_BY_ID);
    }

    public void updateHolidayManagementPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.UPDATE_HOLIDAY_MANAGEMENT);
    }

    public void deleteHolidayManagementPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DELETE_HOLIDAY_MANAGEMENT);
    }

    /* Budget Year Permissions */
    public void addBudgetYearPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.ADD_BUDGET_YEAR);
    }

    public void getAllBudgetYearsPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_BUDGET_YEARS);
    }

    public void getBudgetYearByIdPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_BUDGET_YEAR_BY_ID);
    }

    public void updateBudgetYearPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.UPDATE_BUDGET_YEAR);
    }

    public void deleteBudgetYearPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.DELETE_BUDGET_YEAR);
    }

    /* Employee Leave Balance Permissions */
    public void getEmployeeLeaveBalancePermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_EMPLOYEE_LEAVE_BALANCE);
    }

    public void getAllEmployeeLeaveBalanceHistoriesPermission(UUID tenantId) {
        checkPermission(tenantId, LeaveServiceResourceName.GET_ALL_EMPLOYEE_LEAVE_BALANCE_HISTORIES);
    }

    private void checkPermission(UUID tenantId, LeaveServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}