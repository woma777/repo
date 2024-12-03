package com.saas.auth.data;

import com.saas.auth.dto.clientDto.TenantDto;
import com.saas.auth.enums.PromotionServiceResourceName;
import com.saas.auth.exception.ResourceExistsException;
import com.saas.auth.model.Resource;
import com.saas.auth.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PromotionServiceResource {

    private final ResourceRepository resourceRepository;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        String defaultRole =  "default_role";

        /* Criteria Name */
        resources.add(mapToEntity(PromotionServiceResourceName.ADD_CRITERIA_NAME.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_ALL_CRITERIA_NAMES.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_CRITERIA_NAME_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.UPDATE_CRITERIA_NAME.getValue(),
                null,  tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.DELETE_CRITERIA_NAME.getValue(),
                null, tenant));

        /* Promotion Criteria */
        resources.add(mapToEntity(PromotionServiceResourceName.ADD_PROMOTION_CRITERIA.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_ALL_PROMOTION_CRITERIA.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_PROMOTION_CRITERIA_BY_ID.getValue(),
                defaultRole, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.UPDATE_PROMOTION_CRITERIA.getValue(),
                null,  tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.DELETE_PROMOTION_CRITERIA.getValue(),
                null, tenant));

        /* Candidate */
        resources.add(mapToEntity(PromotionServiceResourceName.ADD_CANDIDATE.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_ALL_CANDIDATES.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_CANDIDATE_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.UPDATE_CANDIDATE.getValue(),
                null,  tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.DELETE_CANDIDATE.getValue(),
                null, tenant));

        /* Candidate Evaluation*/
        resources.add(mapToEntity(PromotionServiceResourceName.ADD_CANDIDATE_EVALUATION.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_ALL_CANDIDATE_EVALUATIONS.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_CANDIDATE_EVALUATION_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.UPDATE_CANDIDATE_EVALUATION.getValue(),
                null,  tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.DELETE_CANDIDATE_EVALUATION.getValue(),
                null, tenant));

        /* Promote Candidate */
        resources.add(mapToEntity(PromotionServiceResourceName.ADD_PROMOTE_CANDIDATE.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_ALL_PROMOTE_CANDIDATES.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.GET_PROMOTE_CANDIDATE_BY_ID.getValue(),
                null, tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.UPDATE_PROMOTE_CANDIDATE.getValue(),
                null,  tenant));
        resources.add(mapToEntity(PromotionServiceResourceName.DELETE_PROMOTE_CANDIDATE.getValue(),
                null, tenant));

        for (Resource resource : resources) {
            if (resourceRepository.existsByTenantIdAndResourceName(tenant.getId(), resource.getResourceName())) {
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
        resource.setServiceName("promotion-service");
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
