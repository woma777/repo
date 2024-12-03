package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.HrPlanningServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class HrPlanningServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        /* Annual Recruitment and Promotion */
        resources.add(mapToEntity(HrPlanningServiceResourceName.ADD_ANNUAL_RECRUITMENT_AND_PROMOTION.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ALL_ANNUAL_RECRUITMENT_AND_PROMOTIONS.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ANNUAL_RECRUITMENT_AND_PROMOTION_BY_ID.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.UPDATE_ANNUAL_RECRUITMENT_AND_PROMOTION.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.DELETE_ANNUAL_RECRUITMENT_AND_PROMOTION.getValue(),
                 tenant));

        /* HR Need Request */
        resources.add(mapToEntity(HrPlanningServiceResourceName.ADD_HR_NEED_REQUEST.getValue(),
                tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ALL_HR_NEED_REQUESTS.getValue(),
                tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_HR_NEED_REQUEST_BY_ID.getValue(),
                tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_HR_NEED_REQUEST_BY_STAFF_PLAN_ID.getValue(),
                tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.UPDATE_HR_NEED_REQUEST.getValue(),
                tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.DELETE_HR_NEED_REQUEST.getValue(),
                tenant));

        /* HR Analysis */
        resources.add(mapToEntity(HrPlanningServiceResourceName.ADD_HR_ANALYSIS.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_ALL_HR_ANALYSES.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.GET_HR_ANALYSIS_BY_ID.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.UPDATE_HR_ANALYSIS.getValue(),
                 tenant));
        resources.add(mapToEntity(HrPlanningServiceResourceName.DELETE_HR_ANALYSIS.getValue(),
                 tenant));

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
                                 TenantDto tenant) {

        Resource resource = new Resource();
        resource.setResourceName(requestName);
        resource.setServiceName("hr-planning-service");
        resource.setTenantId(tenant.getId());
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        String adminRole =  "admin";
        roles.add(adminRole);
        resource.setRequiredRoles(roles);

        return resource;
    }
}
