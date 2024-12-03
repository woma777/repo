package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.OrganizationServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OrganizationServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        /* Tenant */
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_TENANT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_TENANT.getValue(),
                null, tenant));

        /* Work Unit */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_WORK_UNIT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_WORK_UNITS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_WORK_UNIT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_WORK_UNIT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_WORK_UNIT.getValue(),
                null, tenant));

        /* Staff Plan */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_STAFF_PLAN.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_STAFF_PLANS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_STAFF_PLAN_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_STAFF_PLANS_BY_DEPARTMENT_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_STAFF_PLAN.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_STAFF_PLAN.getValue(),
                null, tenant));

        /* Qualification */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_QUALIFICATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_QUALIFICATIONS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_QUALIFICATION_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_QUALIFICATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_QUALIFICATION.getValue(),
                null, tenant));

        /* Pay Grade */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_PAY_GRADE.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_PAY_GRADES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_PAY_GRADE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_PAY_GRADES_BY_JOB_GRADE_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_PAY_GRADE.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_PAY_GRADE.getValue(),
                null, tenant));

        /* Location */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_LOCATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_SUB_LOCATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_LOCATIONS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_LOCATION_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_PARENT_LOCATIONS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_SUB_LOCATIONS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_LOCATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_SUB_LOCATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_LOCATION.getValue(),
                null, tenant));

        /* Job */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_JOB.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_JOBS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOB_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOBS_BY_DEPARTMENT_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_JOB.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_JOB.getValue(),
                null, tenant));

        /* Job Grade */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_JOB_GRADE.getValue(),
                null,  tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_JOB_GRADES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOB_GRADE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_JOB_GRADE.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_JOB_GRADE.getValue(),
                null, tenant));

        /* Job Category */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_JOB_CATEGORY.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_JOB_CATEGORIES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_JOB_CATEGORY_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_JOB_CATEGORY.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_JOB_CATEGORY.getValue(),
                null, tenant));

        /* Field of Study */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_FIELD_OF_STUDY.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_FIELD_OF_STUDIES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_FIELD_OF_STUDY_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_FIELD_OF_STUDY.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_FIELD_OF_STUDY.getValue(),
                null, tenant));

        /* Education Level */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_EDUCATION_LEVEL.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_EDUCATION_LEVELS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_EDUCATION_LEVEL_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_EDUCATION_LEVEL.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_EDUCATION_LEVEL.getValue(),
                null, tenant));

        /* Department */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_DEPARTMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_SUB_DEPARTMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_DEPARTMENTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_DEPARTMENT_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_PARENT_DEPARTMENTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_SUB_DEPARTMENTS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.TRANSFER_PARENT_DEPARTMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.TRANSFER_SUB_DEPARTMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_DEPARTMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_SUB_DEPARTMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_DEPARTMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_DEPARTMENT_HISTORIES.getValue(),
                defaultRole, tenant));

        /* Department Type */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_DEPARTMENT_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_DEPARTMENT_TYPES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_DEPARTMENT_TYPE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_DEPARTMENT_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_DEPARTMENT_TYPE.getValue(),
                null, tenant));

        /* Address */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_ADDRESS.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_ADDRESSES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ADDRESS_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ADDRESSES_BY_DEPARTMENT_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_ADDRESS.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_ADDRESS.getValue(),
                null, tenant));

        /* Location Type */
        resources.add(mapToEntity(OrganizationServiceResourceName.ADD_LOCATION_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_ALL_LOCATION_TYPES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.GET_LOCATION_TYPE_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.UPDATE_LOCATION_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(OrganizationServiceResourceName.DELETE_LOCATION_TYPE.getValue(),
                null, tenant));

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
        resource.setServiceName("organization-service");
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
