package com.saas.recruitment.service;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.ReferenceRequest;
import com.saas.recruitment.dto.response.ReferenceResponse;
import com.saas.recruitment.model.Applicant;
import com.saas.recruitment.model.Reference;
import com.saas.recruitment.exception.ResourceNotFoundException;
import com.saas.recruitment.mapper.ReferenceMapper;
import com.saas.recruitment.repository.ReferenceRepository;
import com.saas.recruitment.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ReferenceMapper referenceMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ReferenceResponse addReference(UUID tenantId,
                                          UUID applicantId,
                                          ReferenceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = referenceMapper.mapToEntity(tenant, applicant, request);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    public List<ReferenceResponse> getAllReferences(UUID tenantId,
                                                    UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        List<Reference> references = referenceRepository
                .findByTenantIdAndApplicant(tenant.getId(), applicant);
        return references.stream()
                .map(referenceMapper::mapToDto)
                .toList();
    }

    public ReferenceResponse getReferenceById(UUID tenantId,
                                              UUID applicantId,
                                              UUID referenceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = getReferenceById(tenant.getId(), applicant, referenceId);
        return referenceMapper.mapToDto(reference);
    }

    @Transactional
    public ReferenceResponse updateReference(UUID tenantId,
                                             UUID applicantId,
                                             UUID referenceId,
                                             ReferenceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = getReferenceById(tenant.getId(), applicant, referenceId);
        reference = referenceMapper.updateReference(reference, request);
        reference = referenceRepository.save(reference);
        return referenceMapper.mapToDto(reference);
    }

    @Transactional
    public void deleteReference(UUID tenantId,
                                UUID applicantId,
                                UUID referenceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Reference reference = getReferenceById(tenant.getId(), applicant, referenceId);
        referenceRepository.delete(reference);
    }

    private Reference getReferenceById(UUID tenantId, Applicant applicant, UUID referenceId) {

        return referenceRepository.findById(referenceId)
                .filter(ref -> ref.getTenantId().equals(tenantId))
                .filter(ref -> ref.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reference not found with id '" + referenceId + "'"));
    }
}