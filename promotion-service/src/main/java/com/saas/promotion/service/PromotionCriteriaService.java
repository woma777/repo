package com.saas.promotion.service;

import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.PromotionCriteriaRequest;
import com.saas.promotion.dto.response.PromotionCriteriaResponse;
import com.saas.promotion.exception.ResourceExistsException;
import com.saas.promotion.exception.ResourceNotFoundException;
import com.saas.promotion.mapper.PromotionCriteriaMapper;
import com.saas.promotion.model.CriteriaName;
import com.saas.promotion.model.PromotionCriteria;
import com.saas.promotion.repository.CriteriaNameRepository;
import com.saas.promotion.repository.PromotionCriteriaRepository;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromotionCriteriaService {

    private final PromotionCriteriaRepository promotionCriteriaRepository;
    private final PromotionCriteriaMapper promotionCriteriaMapper;
    private final ValidationUtil validationUtil;
    private final CriteriaNameRepository criteriaNameRepository;

    @Transactional
    public PromotionCriteriaResponse addPromotionCriteria(UUID tenantId,
                                                          PromotionCriteriaRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName criteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), request.getCriteriaNameId());
        if (promotionCriteriaRepository
                .existsByTenantIdAndCriteriaName(tenant.getId(), criteriaName)) {
            throw new ResourceExistsException("Promotion criteria with criteria name '" +
                    criteriaName.getName() + "' already exists");
        }
        validateTotalWeight(tenant.getId(), request.getWeight(), null);
        PromotionCriteria promotionCriteria = promotionCriteriaMapper
                .mapToEntity(tenant, null, request);
        promotionCriteria = promotionCriteriaRepository.save(promotionCriteria);
        return promotionCriteriaMapper.mapToDto(promotionCriteria);
    }

    @Transactional
    public PromotionCriteriaResponse addSubPromotionCriteria(UUID tenantId,
                                                             UUID parentCriteriaId,
                                                             PromotionCriteriaRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PromotionCriteria parentCriteria = validationUtil
                .getPromotionCriteria(tenant.getId(), parentCriteriaId);
        CriteriaName criteriaName = getCriteriaName(tenant, parentCriteria, request);
        if (promotionCriteriaRepository
                .existsByTenantIdAndParentPromotionCriteriaAndCriteriaName(
                        tenant.getId(), parentCriteria, criteriaName)) {
            throw new ResourceExistsException("Sub promotion criteria with criteria name '" +
                    criteriaName.getName() + "' already exists");
        }
        validateSubTotalWeight(tenant.getId(), parentCriteria, request.getWeight(), null);
        PromotionCriteria promotionCriteria = promotionCriteriaMapper
                .mapToEntity(tenant, parentCriteria, request);
        promotionCriteria = promotionCriteriaRepository.save(promotionCriteria);
        return promotionCriteriaMapper.mapToDto(promotionCriteria);
    }

    public List<PromotionCriteriaResponse> getAllPromotionCriteria(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<PromotionCriteria> promotionCriteriaList = promotionCriteriaRepository
                .findByTenantId(tenant.getId());
        return promotionCriteriaList.stream()
                .filter(pc -> pc.getParentPromotionCriteria() == null)
                .map(promotionCriteriaMapper::mapToDto)
                .toList();
    }

    public List<PromotionCriteriaResponse> getAllSubPromotionCriteria(UUID tenantId,
                                                                      UUID parentCriteriaId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PromotionCriteria parentCriteria = validationUtil
                .getPromotionCriteria(tenant.getId(), parentCriteriaId);
        List<PromotionCriteria> promotionCriteriaList = promotionCriteriaRepository
                .findByTenantIdAndParentPromotionCriteria(tenant.getId(), parentCriteria);
        return promotionCriteriaList.stream()
                .map(promotionCriteriaMapper::mapToDto)
                .toList();
    }

    public PromotionCriteriaResponse getPromotionCriteriaById(UUID tenantId,
                                                              UUID criteriaId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PromotionCriteria promotionCriteria = validationUtil
                .getPromotionCriteria(tenant.getId(), criteriaId);
        return promotionCriteriaMapper.mapToDto(promotionCriteria);
    }

    @Transactional
    public PromotionCriteriaResponse updatePromotionCriteria(UUID tenantId,
                                                             UUID criteriaId,
                                                             PromotionCriteriaRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName criteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), request.getCriteriaNameId());
        PromotionCriteria promotionCriteria = validationUtil
                .getPromotionCriteria(tenant.getId(), criteriaId);
        if (promotionCriteriaRepository.existsByTenantIdAndCriteriaNameAndIdNot(
                tenant.getId(), criteriaName, promotionCriteria.getId())) {
            throw new ResourceExistsException("Promotion criteria with criteria name '" +
                    criteriaName.getName() + "' already exists");
        }
        PromotionCriteria parentPromotion = promotionCriteria.getParentPromotionCriteria();
        if (parentPromotion == null) {
            validateTotalWeight(tenant.getId(), request.getWeight(), criteriaId);
        } else {
            validateSubTotalWeight(tenant.getId(), parentPromotion, request.getWeight(), criteriaId);
        }
        promotionCriteria = promotionCriteriaMapper.updateEntity(tenant, promotionCriteria, request);
        promotionCriteria = promotionCriteriaRepository.save(promotionCriteria);
        return promotionCriteriaMapper.mapToDto(promotionCriteria);
    }

    @Transactional
    public void deletePromotionCriteriaById(UUID tenantId,
                                            UUID criteriaId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PromotionCriteria promotionCriteria = validationUtil
                .getPromotionCriteria(tenant.getId(), criteriaId);
        promotionCriteriaRepository.delete(promotionCriteria);
    }

    private void validateTotalWeight(UUID tenantId, double newWeight, UUID criteriaId) {

        List<PromotionCriteria> existingCriteria = promotionCriteriaRepository.findByTenantId(tenantId);
        double totalWeight;
        if (criteriaId == null) {
            totalWeight = existingCriteria.stream()
                    .mapToDouble(PromotionCriteria::getWeight)
                    .sum();
        } else {
            totalWeight = existingCriteria.stream()
                    .filter(pc -> !pc.getId().equals(criteriaId))
                    .mapToDouble(PromotionCriteria::getWeight)
                    .sum();
        }
        if (totalWeight + newWeight > 100) {
            throw new IllegalArgumentException("Total weight of promotion criteria exceeds 100");
        }
    }

    private void validateSubTotalWeight(UUID tenantId, PromotionCriteria parentCriteria,
                                        double newWeight, UUID criteriaId) {

        List<PromotionCriteria> existingCriteria = promotionCriteriaRepository
                .findByTenantIdAndParentPromotionCriteria(tenantId, parentCriteria);
        double totalWeight;
        if (criteriaId == null) {
            totalWeight = existingCriteria.stream()
                    .mapToDouble(PromotionCriteria::getWeight)
                    .sum();
        } else {
            totalWeight = existingCriteria.stream()
                    .filter(pc -> !pc.getId().equals(criteriaId))
                    .mapToDouble(PromotionCriteria::getWeight)
                    .sum();
        }
        if (totalWeight + newWeight > parentCriteria.getWeight()) {
            throw new IllegalArgumentException(
                    "Total weight of sub promotion criteria exceeds " + parentCriteria.getWeight());
        }
    }

    private CriteriaName getCriteriaName(TenantDto tenant,
                                         PromotionCriteria parentCriteria,
                                         PromotionCriteriaRequest request) {

        List<CriteriaName> criteriaNames = criteriaNameRepository
                .findByTenantIdAndParentCriteriaName(tenant.getId(),
                        parentCriteria.getCriteriaName());
        for (CriteriaName criteriaName : criteriaNames) {
            if (criteriaName.getId().equals(request.getCriteriaNameId())) {
                return criteriaName;
            }
        }
        throw new ResourceNotFoundException(
                "No matching criteria name found for the given parent criteria.");
    }
}
