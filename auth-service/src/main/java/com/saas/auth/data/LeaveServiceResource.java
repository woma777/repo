package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.LeaveServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class LeaveServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        /* Leave Type */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_TYPES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_TYPE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_TYPE.getValue(),
                null, tenant));

        /* Leave Setting */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_SETTING.getValue(),
                null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_SETTINGS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_SETTING_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_SETTING.getValue(),
                null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_SETTING.getValue(),
                null, tenant));

        /* Leave Schedule */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_SCHEDULE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_SCHEDULES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_SCHEDULE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_SCHEDULE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_SCHEDULE.getValue(),
                defaultRole, tenant));

        /* Leave Request */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_REQUEST.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_REQUESTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_REQUEST_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_REQUEST.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DEPARTMENT_APPROVE_LEAVE_REQUEST.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.HR_APPROVE_LEAVE_REQUEST.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_REQUESTS_BY_STATUS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_REQUEST.getValue(),
                defaultRole, tenant));

        /* Holiday */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_HOLIDAY.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_HOLIDAYS.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_HOLIDAY_BY_ID.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_HOLIDAY.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_HOLIDAY.getValue(),
                 null, tenant));

        /* Holiday Management */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_HOLIDAY_MANAGEMENT.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_HOLIDAY_MANAGEMENTS.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_HOLIDAY_MANAGEMENT_BY_ID.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_HOLIDAY_MANAGEMENT.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_HOLIDAY_MANAGEMENT.getValue(),
                 null, tenant));

        /* Budget Year */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_BUDGET_YEAR.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_BUDGET_YEARS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_BUDGET_YEAR_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_BUDGET_YEAR.getValue(),
                 null, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_BUDGET_YEAR.getValue(),
                 null, tenant));

        /* Employee Leave Balance */
        resources.add(mapToEntity(LeaveServiceResourceName.GET_EMPLOYEE_LEAVE_BALANCE.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_EMPLOYEE_LEAVE_BALANCE_HISTORIES.getValue(),
                defaultRole, tenant));

        for (Resource resource : resources) {
            if (resourceRepository
                    .existsByTenantIdAndResourceName(tenant.getId(), resource.getResourceName())) {
                throw new ResourceExistsException(
                        "Resource already exists with name '" + resource.getResourceName() + "'");
            }
            resourceRepository.save(resource);
        }
    }

    private Resource mapToEntity(String requestName,
                                 String roleName,
                                 TenantDto tenant) {

        Resource resource = new Resource();
        resource.setResourceName(requestName);
        resource.setServiceName("leave-service");
        resource.setTenantId(tenant.getId());
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        String adminRole =  "admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}
