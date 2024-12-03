package com.saas.organization.service;

import com.saas.organization.dto.requestDto.EducationLevelRequest;
import com.saas.organization.dto.responseDto.EducationLevelResponse;
import com.saas.organization.model.EducationLevel;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.EducationLevelMapper;
import com.saas.organization.repository.EducationLevelRepository;
import com.saas.organization.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationLevelService {

    private final EducationLevelRepository educationLevelRepository;
    private final EducationLevelMapper educationLevelMapper;
    private final TenantRepository tenantRepository;

    public EducationLevelResponse createEducationLevel(UUID tenantId,
                                                       EducationLevelRequest educationLevelRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));
        if (educationLevelRepository.existsByEducationLevelNameAndTenantId(
                educationLevelRequest.getEducationLevelName(), tenant.getId())) {
            throw new ResourceExistsException("Education Level with Name " +
                    educationLevelRequest.getEducationLevelName() + " already exists");
        }

        EducationLevel educationLevel = educationLevelMapper.mapToEntity(educationLevelRequest);
        educationLevel.setTenant(tenant);
        educationLevel = educationLevelRepository.save(educationLevel);
        return educationLevelMapper.mapToDto(educationLevel);
    }

    public List<EducationLevelResponse> getAllEducationLevels(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));
        List<EducationLevel> educationLevels = educationLevelRepository.findByTenant(tenant);
        return educationLevels.stream()
                .map(educationLevelMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public EducationLevelResponse getEducationLevelById(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));
        EducationLevel educationLevel = educationLevelRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education level not found with id: " + id + " for the specified tenant "));
        return educationLevelMapper.mapToDto(educationLevel);
    }

    @Transactional
    public EducationLevelResponse updateEducationLevel(UUID id, UUID tenantId,
                                                       EducationLevelRequest educationLevelRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));
        EducationLevel educationLevel = educationLevelRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education level not found with id: " + id + " for the specified tenant "));
        educationLevel = educationLevelMapper.updateEducationLevel(educationLevel, educationLevelRequest);
        educationLevel = educationLevelRepository.save(educationLevel);
        return educationLevelMapper.mapToDto(educationLevel);
    }

    @Transactional
    public void deleteEducationLevel(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));
        EducationLevel educationLevel = educationLevelRepository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education level not found with id: " + id + " for the specified tenant "));
        educationLevelRepository.delete(educationLevel);
    }
}