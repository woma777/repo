package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.UniversityRequest;
import com.saas.training.dto.response.UniversityResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.repository.UniversityRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.University;
import com.saas.training.mapper.UniversityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityMapper universityMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public UniversityResponse addUniversity(UUID tenantId,
                                            UniversityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = universityMapper.mapToEntity(tenant, request);
        if (universityRepository.existsByTenantIdAndUniversityName(
                tenant.getId(), request.getUniversityName())) {
            throw new ResourceExistsException(
                    "University with name '" + request.getUniversityName() + "' already exists");
        }
        university = universityRepository.save(university);
        return universityMapper.mapToDto(university);
    }

    public List<UniversityResponse> getAllUniversities(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<University> universities = universityRepository.findAll();
        return universities.stream()
                .filter(university -> university.getTenantId().equals(tenant.getId()))
                .map(universityMapper::mapToDto)
                .toList();
    }

    public UniversityResponse getUniversityById(UUID tenantId,
                                                UUID universityId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = validationUtil.getUniversityById(tenant.getId(), universityId);
        return universityMapper.mapToDto(university);
    }

    @Transactional
    public UniversityResponse updateUniversity(UUID tenantId,
                                               UUID universityId,
                                               UniversityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = validationUtil.getUniversityById(tenant.getId(), universityId);
        if (universityRepository.existsByTenantIdAndUniversityNameAndIdNot(
                tenant.getId(), request.getUniversityName(), university.getId())) {
            throw new ResourceExistsException(
                    "University with name '" + request.getUniversityName() + "' already exists");
        }
        university = universityMapper.updateEntity(tenant, university, request);
        university = universityRepository.save(university);
        return universityMapper.mapToDto(university);
    }

    @Transactional
    public void deleteUniversity(UUID tenantId,
                                 UUID universityId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        University university = validationUtil.getUniversityById(tenant.getId(), universityId);
        universityRepository.delete(university);
    }
}