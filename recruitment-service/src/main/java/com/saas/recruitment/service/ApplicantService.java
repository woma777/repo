package com.saas.recruitment.service;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.ApplicantRequest;
import com.saas.recruitment.dto.response.ApplicantResponse;
import com.saas.recruitment.model.Recruitment;
import com.saas.recruitment.model.Applicant;
import com.saas.recruitment.enums.RecruitmentStatus;
import com.saas.recruitment.mapper.ApplicantMapper;
import com.saas.recruitment.repository.ApplicantRepository;
import com.saas.recruitment.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ApplicantResponse createApplicant(UUID tenantId,
                                             ApplicantRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create applicant for non-approved recruitment.");
        }
        Applicant applicant = applicantMapper.mapToEntity(tenant, recruitment, request);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.mapToDto(applicant);
    }

    public List<ApplicantResponse> getAllApplicants(UUID tenantId,
                                                    UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        List<Applicant> applicants = applicantRepository
                .findByTenantIdAndRecruitment(tenant.getId(), recruitment);
        return applicants.stream()
                .map(applicantMapper::mapToDto)
                .toList();
    }

    public ApplicantResponse getApplicantById(UUID tenantId,
                                              UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        return applicantMapper.mapToDto(applicant);
    }

    @Transactional
    public ApplicantResponse updateApplicant(UUID tenantId,
                                             UUID applicantId,
                                             ApplicantRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        applicant = applicantMapper.updateApplicant(tenant, applicant, request);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.mapToDto(applicant);
    }

    @Transactional
    public void deleteApplicant(UUID tenantId,
                                UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        applicantRepository.delete(applicant);
    }
}