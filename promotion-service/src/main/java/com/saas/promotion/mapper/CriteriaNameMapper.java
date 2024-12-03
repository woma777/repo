package com.saas.promotion.mapper;

import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.CriteriaNameRequest;
import com.saas.promotion.dto.response.CriteriaNameResponse;
import com.saas.promotion.model.CriteriaName;
import org.springframework.stereotype.Component;

@Component
public class CriteriaNameMapper {

    public CriteriaName mapToEntity(TenantDto tenant,
                                    CriteriaName parentCriteriaName,
                                    CriteriaNameRequest request) {

        CriteriaName criteriaName = new CriteriaName();
        criteriaName.setName(request.getName());
        criteriaName.setDescription(request.getDescription());
        criteriaName.setParentCriteriaName(parentCriteriaName);
        criteriaName.setTenantId(tenant.getId());

        return criteriaName;
    }

    public CriteriaNameResponse mapToDto(CriteriaName criteriaName) {

        CriteriaNameResponse response = new CriteriaNameResponse();
        response.setId(criteriaName.getId());
        response.setName(criteriaName.getName());
        response.setDescription(criteriaName.getDescription());
        response.setTenantId(criteriaName.getTenantId());
        response.setCreatedAt(criteriaName.getCreatedAt());
        response.setUpdatedAt(criteriaName.getUpdatedAt());
        response.setCreatedBy(criteriaName.getCreatedBy());
        response.setUpdatedBy(criteriaName.getUpdatedBy());

        if (criteriaName.getParentCriteriaName() != null) {
            response.setParentCriteriaNameId(criteriaName.getParentCriteriaName().getId());
        } else {
            response.setParentCriteriaNameId(null);
        }

        return response;
    }

    public CriteriaName updateEntity(CriteriaName criteriaName,
                                     CriteriaNameRequest request) {

        if (request.getName() != null) {
            criteriaName.setName(request.getName());
        }
        if (request.getDescription() != null) {
            criteriaName.setDescription(request.getDescription());
        }

        return criteriaName;
    }
}
