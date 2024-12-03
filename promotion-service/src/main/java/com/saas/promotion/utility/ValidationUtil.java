package com.saas.promotion.utility;

import com.saas.promotion.client.EmployeeServiceClient;
import com.saas.promotion.client.OrganizationServiceClient;
import com.saas.promotion.client.RecruitmentServiceClient;
import com.saas.promotion.dto.clientDto.*;
import com.saas.promotion.exception.ResourceNotFoundException;
import com.saas.promotion.model.Candidate;
import com.saas.promotion.model.PromotionCriteria;
import com.saas.promotion.model.CriteriaName;
import com.saas.promotion.repository.CandidateRepository;
import com.saas.promotion.repository.CriteriaNameRepository;
import com.saas.promotion.repository.PromotionCriteriaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final CriteriaNameRepository criteriaNameRepository;
    private final RecruitmentServiceClient recruitmentServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final CandidateRepository candidateRepository;
    private final PromotionCriteriaRepository promotionCriteriaRepository;

    public TenantDto getTenantById(UUID tenantId) {

        try {
            return organizationServiceClient.getTenantById(tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'");
        }
    }

    public RecruitmentDto getRecruitmentById(UUID tenantId,
                                             UUID recruitmentId) {

        try {
            return recruitmentServiceClient.getRecruitmentById(tenantId, recruitmentId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Recruitment not found with id '" + recruitmentId + "'");
        }
    }

    public RecruitmentDto getRecruitmentByVacancyNumber(UUID tenantId,
                                                        String vacancyNumber) {

        String recruitmentMode = "INTERNAL";
        List<RecruitmentDto> recruitments = recruitmentServiceClient
                .getRecruitmentsByMode(tenantId, recruitmentMode);
        for (RecruitmentDto recruitment : recruitments) {
            if (recruitment.getVacancyNumber().equals(vacancyNumber)) {
                if (recruitment.getRecruitmentStatus().equals("Approved")) {
                    return recruitment;
                }
                throw new IllegalStateException(
                        "Recruitment with vacancy number '" + vacancyNumber +
                                "' is found but not approved.");
            }
        }
        throw new ResourceNotFoundException(
                "Recruitment not found with vacancy number '" + vacancyNumber +
                        "' in the list of internal recruitments.");
    }

    public PayGradeDto getPayGradeById(UUID tenantId,
                                       UUID jobId,
                                       UUID payGradeId) {
        JobDto job;
        try {
            job = organizationServiceClient.getJobById(jobId, tenantId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Job not found with id '" + jobId + "'");
        }
        try {
            List<PayGradeDto> payGrades = organizationServiceClient
                    .getPayGradesByJobGradeId(job.getJobGradeId(), tenantId);
            PayGradeDto payGrade = organizationServiceClient.getPayGradeById(payGradeId, tenantId);
            if (payGrades.contains(payGrade)) {
                return payGrade;
            }
            throw new ResourceNotFoundException("Pay grade not found in the specified job");
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Pay grade not found with id '" + payGradeId + "'");
        }
    }

    public EmployeeDto getEmployeeByEmployeeId(UUID tenantId,
                                               String employeeId) {

        try {
            return employeeServiceClient.getEmployeeByEmployeeId(tenantId, employeeId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Employee not found with employee id '" + employeeId + "'");
        }
    }

    public EmployeeDto getEmployeeById(UUID tenantId,
                                       UUID employeeId) {

        try {
            return employeeServiceClient.getEmployeeById(tenantId, employeeId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Employee not found with id '" + employeeId + "'");
        }
    }

    public Candidate getCandidateById(UUID tenantId,
                                      UUID promotionId) {

        return candidateRepository.findById(promotionId)
                .filter(ap -> ap.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Apply promotion not found with id '" + promotionId + "'"));
    }

    public CriteriaName getCriteriaNameById(UUID tenantId,
                                            UUID criteriaNameId) {

        return criteriaNameRepository.findById(criteriaNameId)
                .filter(cn -> cn.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Promotion criteria name not found with id '" + criteriaNameId + "'"));
    }

    public PromotionCriteria getPromotionCriteria(UUID tenantId,
                                                  UUID promotionCriteriaId) {

        return promotionCriteriaRepository.findById(promotionCriteriaId)
                .filter(pc -> pc.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Promotion criteria not found with id '" + promotionCriteriaId + "'"));
    }
}
