package com.saas.promotion.mapper;

import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.PromotionCriteriaRequest;
import com.saas.promotion.dto.response.PromotionCriteriaResponse;
import com.saas.promotion.model.CriteriaName;
import com.saas.promotion.model.PromotionCriteria;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionCriteriaMapper {

    private final ValidationUtil validationUtil;
    private final CriteriaNameMapper criteriaNameMapper;

    public PromotionCriteria mapToEntity(TenantDto tenant,
                                         PromotionCriteria parentCriteria,
                                         PromotionCriteriaRequest request) {

        CriteriaName criteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), request.getCriteriaNameId());

        PromotionCriteria promotionCriteria = new PromotionCriteria();
        promotionCriteria.setTenantId(tenant.getId());
        promotionCriteria.setCriteriaName(criteriaName);
        promotionCriteria.setWeight(request.getWeight());
        promotionCriteria.setParentPromotionCriteria(parentCriteria);

        return promotionCriteria;
    }

    public PromotionCriteriaResponse mapToDto(PromotionCriteria promotionCriteria) {

        PromotionCriteriaResponse response = new PromotionCriteriaResponse();
        response.setId(promotionCriteria.getId());
        response.setCriteriaName(criteriaNameMapper.mapToDto(promotionCriteria.getCriteriaName()));
        response.setWeight(promotionCriteria.getWeight());
        response.setTenantId(promotionCriteria.getTenantId());
        response.setCreatedAt(promotionCriteria.getCreatedAt());
        response.setUpdatedAt(promotionCriteria.getUpdatedAt());
        response.setCreatedBy(promotionCriteria.getCreatedBy());
        response.setUpdatedBy(promotionCriteria.getUpdatedBy());

        if (promotionCriteria.getParentPromotionCriteria() != null) {
            response.setParentCriteriaId(promotionCriteria.getParentPromotionCriteria().getId());
        } else {
            response.setParentCriteriaId(null);
        }

        return response;
    }

    public PromotionCriteria updateEntity(TenantDto tenant,
                                          PromotionCriteria promotionCriteria,
                                          PromotionCriteriaRequest request) {

        CriteriaName criteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), request.getCriteriaNameId());

        if (request.getCriteriaNameId() != null) {
            promotionCriteria.setCriteriaName(criteriaName);
        }

        if (request.getWeight() > 0) {
            promotionCriteria.setWeight(request.getWeight());
        }

        return promotionCriteria;
    }
}
