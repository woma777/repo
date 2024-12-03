package com.saas.promotion.service;

import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.CriteriaNameRequest;
import com.saas.promotion.dto.response.CriteriaNameResponse;
import com.saas.promotion.exception.ResourceExistsException;
import com.saas.promotion.mapper.CriteriaNameMapper;
import com.saas.promotion.model.CriteriaName;
import com.saas.promotion.repository.CriteriaNameRepository;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CriteriaNameService {

    private final CriteriaNameRepository criteriaNameRepository;
    private final CriteriaNameMapper criteriaNameMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public CriteriaNameResponse addCriteriaName(UUID tenantId,
                                                CriteriaNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName criteriaName = criteriaNameMapper
                .mapToEntity(tenant, null, request);
        if (criteriaNameRepository.existsByTenantIdAndName(
                tenant.getId(), request.getName())) {
            throw new ResourceExistsException(
                    "Promotion criteria name already exists with name '" + request.getName() + "'");
        }
        criteriaName = criteriaNameRepository.save(criteriaName);
        return criteriaNameMapper.mapToDto(criteriaName);
    }

    @Transactional
    public CriteriaNameResponse addSubCriteriaName(UUID tenantId,
                                                   UUID parentCriteriaNameId,
                                                   CriteriaNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName parentCriteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), parentCriteriaNameId);
        if (criteriaNameRepository.existsByTenantIdAndParentCriteriaNameAndName(
                tenant.getId(), parentCriteriaName, request.getName())) {
            throw new ResourceExistsException(
                    "Promotion sub criteria name already exists with name '" + request.getName() + "'");
        }
        CriteriaName criteriaName = criteriaNameMapper
                .mapToEntity(tenant, parentCriteriaName, request);
        criteriaName = criteriaNameRepository.save(criteriaName);
        return criteriaNameMapper.mapToDto(criteriaName);
    }

    public List<CriteriaNameResponse> getAllCriteriaNames(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<CriteriaName> criteriaNames = criteriaNameRepository.findByTenantId(tenant.getId());
        return criteriaNames.stream()
                .filter(cn -> cn.getParentCriteriaName() == null)
                .map(criteriaNameMapper::mapToDto)
                .toList();
    }

    public List<CriteriaNameResponse> getAllSubCriteriaNames(UUID tenantId,
                                                             UUID parentCriteriaNameId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName parentCriteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), parentCriteriaNameId);
        List<CriteriaName> criteriaNames = criteriaNameRepository
                .findByTenantIdAndParentCriteriaName(tenant.getId(), parentCriteriaName);
        return criteriaNames.stream()
                .map(criteriaNameMapper::mapToDto)
                .toList();
    }

    public CriteriaNameResponse getCriteriaNameById(UUID tenantId,
                                                    UUID criteriaNameId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName criteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), criteriaNameId);
        return criteriaNameMapper.mapToDto(criteriaName);
    }

    @Transactional
    public CriteriaNameResponse updateCriteriaName(UUID tenantId,
                                                   UUID criteriaNameId,
                                                   CriteriaNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName criteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), criteriaNameId);
        if (criteriaNameRepository.existsByTenantIdAndNameAndIdNot(
                tenant.getId(), request.getName(), criteriaName.getId())) {
            throw new ResourceExistsException(
                    "Promotion criteria name already exists with name '" + request.getName() + "'");
        }
        criteriaName = criteriaNameMapper.updateEntity(criteriaName, request);
        criteriaName = criteriaNameRepository.save(criteriaName);
        return criteriaNameMapper.mapToDto(criteriaName);
    }

    @Transactional
    public void deleteCriteriaName(UUID tenantId, UUID criteriaNameId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CriteriaName criteriaName = validationUtil
                .getCriteriaNameById(tenant.getId(), criteriaNameId);
        criteriaNameRepository.delete(criteriaName);
    }
}
