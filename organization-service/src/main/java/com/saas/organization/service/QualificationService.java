package com.saas.organization.service;

import com.saas.organization.dto.requestDto.QualificationRequest;
import com.saas.organization.dto.responseDto.QualificationResponse;
import com.saas.organization.model.JobRegistration;
import com.saas.organization.model.Qualification;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.QualificationMapper;
import com.saas.organization.repository.QualificationRepository;
import com.saas.organization.repository.TenantRepository;
import com.saas.organization.repository.JobRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QualificationService {

    private final QualificationRepository qualificationRepository;
    private final QualificationMapper qualificationMapper;
    private final TenantRepository tenantRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    public QualificationResponse createQualification(UUID tenantId,
                                                     QualificationRequest qualificationRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        if (qualificationRepository.existsByQualificationAndTenantId(
                qualificationRequest.getQualification(), tenant.getId())) {
            throw new ResourceExistsException("Qualification with Name " +
                    qualificationRequest.getQualification() + " already exists");
        }

        Qualification qualification = qualificationMapper.mapToEntity(qualificationRequest);
        qualification.setTenant(tenant);

        qualification = qualificationRepository.save(qualification);

        return qualificationMapper.mapToDto(qualification);
    }

    public List<QualificationResponse> getAllQualifications(UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<Qualification> qualifications = qualificationRepository.findAll();
        return qualifications.stream()
                .filter(qualification -> qualification.getTenant().getId().equals(tenant.getId()))
                .map(qualificationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public QualificationResponse getQualificationById(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        Qualification qualification = qualificationRepository.findById(id)
                .filter(q -> q.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Qualification not found with id: " + id + " for the specified tenant"));

        return qualificationMapper.mapToDto(qualification);
    }

    public QualificationResponse updateQualification(UUID id, UUID tenantId,
                                                     QualificationRequest qualificationRequest) {
        Tenant tenant = getTenantById(tenantId);

        Qualification qualification = qualificationRepository.findById(id)
                .filter(q -> q.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Qualification not found with id: " + id + " for the specified tenant"));

        qualification = qualificationMapper.updateQualification(qualification, qualificationRequest);

        qualification = qualificationRepository.save(qualification);

        return qualificationMapper.mapToDto(qualification);
    }

    public void deleteQualification(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        Qualification qualification = qualificationRepository.findById(id)
                .filter(q -> q.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Qualification not found with id: " + id + " for the specified tenant"));

        qualificationRepository.delete(qualification);
    }

    private Tenant getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }

    private JobRegistration getJobRegistrationById(UUID jobRegistrationId) {
        return jobRegistrationRepository.findById(jobRegistrationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobRegistration not found with id: " + jobRegistrationId));
    }
}