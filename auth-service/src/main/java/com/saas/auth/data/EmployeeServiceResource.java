package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.EmployeeServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class EmployeeServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        /* Employee */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_EMPLOYEE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_EMPLOYEES.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEE_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEE_BY_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEE_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_EMPLOYEE_IMAGE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEES_BY_DEPARTMENT_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEES_BY_EMPLOYMENT_TYPE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEES_BY_DUTY_STATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_EMPLOYEE_HISTORY.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EMPLOYEE_HISTORIES.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_EMPLOYEE_EMAIL.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_EMPLOYEE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_EMPLOYEE.getValue(),
                null, tenant));

        /* Employee Training or Certificate */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_TRAINING.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_TRAININGS.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_TRAINING_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_TRAININGS_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_TRAINING.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_TRAINING.getValue(),
                null, tenant));

        /* Title Name */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_TITLE_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_TITLE_NAMES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_TITLE_NAME_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_TITLE_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_TITLE_NAME.getValue(),
                null, tenant));

        /* Employee Skill */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_SKILL.getValue(),
                 null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_SKILLS.getValue(),
                 null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_SKILL_BY_ID.getValue(),
                 null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_SKILLS_BY_EMPLOYEE_ID.getValue(),
                 null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_SKILL.getValue(),
                 null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_SKILL.getValue(),
                 null, tenant));

        /* Employee Reference */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_REFERENCE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_REFERENCES.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_REFERENCE_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_REFERENCES_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_REFERENCE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_REFERENCE.getValue(),
                null, tenant));

        /* Employee Language */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_LANGUAGE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_LANGUAGES.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_LANGUAGE_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_LANGUAGES_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_LANGUAGE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_LANGUAGE.getValue(),
                null, tenant));

        /* Language Name */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_LANGUAGE_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_LANGUAGE_NAMES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_LANGUAGE_NAME_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_LANGUAGE_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_LANGUAGE_NAME.getValue(),
                null, tenant));

        /* Employee Family */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_FAMILY.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_FAMILIES.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_FAMILY_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_FAMILIES_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_FAMILY.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_FAMILY.getValue(),
                null, tenant));

        /* Employee Experience */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_EXPERIENCE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_EXPERIENCES.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EXPERIENCE_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EXPERIENCES_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_EXPERIENCE.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_EXPERIENCE.getValue(),
                null, tenant));

        /* Employee Education */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_EDUCATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_EDUCATIONS.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EDUCATION_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_EDUCATIONS_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_EDUCATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_EDUCATION.getValue(),
                null, tenant));

        /* Employee Address */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_ADDRESS.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_ADDRESSES.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ADDRESS_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ADDRESSES_BY_EMPLOYEE_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_ADDRESS.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_ADDRESS.getValue(),
                null, tenant));

        /* Duty Station */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_DUTY_STATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_DUTY_STATIONS.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_DUTY_STATION_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_DUTY_STATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_DUTY_STATION.getValue(),
                null, tenant));

        /* Country */
        resources.add(mapToEntity(EmployeeServiceResourceName.ADD_COUNTRY.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_ALL_COUNTRIES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.GET_COUNTRY_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.UPDATE_COUNTRY.getValue(),
                null, tenant));
        resources.add(mapToEntity(EmployeeServiceResourceName.DELETE_COUNTRY.getValue(),
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
        resource.setServiceName("employee-service");
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