package com.saas.recruitment.service;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.ExamResultRequest;
import com.saas.recruitment.dto.response.ExamResultResponse;
import com.saas.recruitment.model.Applicant;
import com.saas.recruitment.model.ExamResult;
import com.saas.recruitment.model.Recruitment;
import com.saas.recruitment.exception.ResourceExistsException;
import com.saas.recruitment.exception.ResourceNotFoundException;
import com.saas.recruitment.mapper.ExamResultMapper;
import com.saas.recruitment.repository.ExamResultRepository;
import com.saas.recruitment.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper examResultMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public ExamResultResponse createExamResult(UUID tenantId,
                                               UUID recruitmentId,
                                               UUID applicantId,
                                               ExamResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        boolean examResultExists = examResultRepository
                .existsByTenantIdAndApplicant(tenant.getId(), applicant);
        if (examResultExists) {
            throw new ResourceExistsException("Exam Result already exists for the specified applicant");
        }
        ExamResult examResult = examResultMapper.mapToEntity(tenant, recruitment, applicant, request);
        examResult = examResultRepository.save(examResult);
        return examResultMapper.mapToDto(examResult);
    }

    public List<ExamResultResponse> getAllExamResult(UUID tenantId,
                                                     UUID recruitmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        List<ExamResult> examResults = examResultRepository
                .findByTenantIdAndRecruitment(tenant.getId(), recruitment);
        return examResults.stream()
                .map(examResultMapper::mapToDto)
                .toList();
    }

    public ExamResultResponse getExamResultById(UUID tenantId,
                                                UUID recruitmentId,
                                                UUID applicantId,
                                                UUID examResultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        ExamResult examResult = getExamResultById(tenant.getId(), recruitment, applicant, examResultId);
        return examResultMapper.mapToDto(examResult);
    }

    @Transactional
    public ExamResultResponse updateExamResult(UUID tenantId,
                                               UUID recruitmentId,
                                               UUID applicantId,
                                               UUID examResultId,
                                               ExamResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        ExamResult examResult = getExamResultById(tenant.getId(), recruitment, applicant, examResultId);
        examResult = examResultMapper.updateExamResult(tenant, recruitment, examResult, request);
        examResult = examResultRepository.save(examResult);
        return examResultMapper.mapToDto(examResult);
    }

    @Transactional
    public void deleteExamResult(UUID tenantId,
                                 UUID recruitmentId,
                                 UUID applicantId,
                                 UUID examResultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil.getRecruitmentById(tenant.getId(), recruitmentId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        ExamResult examResult = getExamResultById(tenant.getId(), recruitment, applicant, examResultId);
        examResultRepository.delete(examResult);
    }

    private ExamResult getExamResultById(UUID tenantId, Recruitment recruitment,
                                         Applicant applicant, UUID examResultId) {

        return examResultRepository.findById(examResultId)
                .filter(exa -> exa.getTenantId().equals(tenantId))
                .filter(exa -> exa.getRecruitment().equals(recruitment))
                .filter(exa -> exa.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam result not found with id '" + examResultId + "'"));
    }
}