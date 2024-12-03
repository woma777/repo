package com.saas.auth.utility;

import com.saas.auth.enums.AuthServiceResourceName;
import com.saas.auth.enums.EmployeeServiceResourceName;
import com.saas.auth.enums.OrganizationServiceResourceName;
import com.saas.auth.enums.RecruitmentServiceResourceName;
import com.saas.auth.exception.ResourceNotFoundException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResourceUtil {

    private final ResourceRepository resourceRepository;

    public void assignRoleToRelatedResources(UUID tenantId, String resourceName, Set<String> roles) {

        Map<String, List<String>> resourceMap = Map.of(
                EmployeeServiceResourceName.ADD_EMPLOYEE.getValue(), List.of(
                        OrganizationServiceResourceName.GET_ALL_DEPARTMENTS.getValue(),
                        OrganizationServiceResourceName.GET_JOBS_BY_DEPARTMENT_ID.getValue(),
                        OrganizationServiceResourceName.GET_PAY_GRADES_BY_JOB_GRADE_ID.getValue()
                ),
                EmployeeServiceResourceName.UPDATE_EMPLOYEE.getValue(), List.of(
                        AuthServiceResourceName.UPDATE_USER.getValue()
                ),
                RecruitmentServiceResourceName.ADD_RECRUITMENT.getValue(), List.of(
                        EmployeeServiceResourceName.GET_EMPLOYEE_BY_EMPLOYEE_ID.getValue()
                )
        );

        List<String> relatedResourceNames = resourceMap.get(resourceName);
        if (relatedResourceNames != null) {
            relatedResourceNames.forEach(name -> {
                Resource resource = getResourceByName(tenantId, name);
                saveRolesToResource(resource, roles);
            });
        }
    }

    private Resource getResourceByName(UUID tenantId, String resourceName) {
        return resourceRepository.findByTenantIdAndResourceName(tenantId, resourceName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with name '" + resourceName + "'"));
    }

    private void saveRolesToResource(Resource resource, Set<String> roles) {
        resource.setRequiredRoles(roles);
        resourceRepository.save(resource);
    }
}