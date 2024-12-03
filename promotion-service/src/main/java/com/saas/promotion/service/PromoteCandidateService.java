package com.saas.promotion.service;

import com.saas.promotion.client.EmployeeServiceClient;
import com.saas.promotion.dto.clientDto.EmployeeDto;
import com.saas.promotion.dto.clientDto.PayGradeDto;
import com.saas.promotion.dto.clientDto.RecruitmentDto;
import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.PromoteCandidateRequest;
import com.saas.promotion.dto.response.PromoteCandidateResponse;
import com.saas.promotion.exception.ResourceExistsException;
import com.saas.promotion.exception.ResourceNotFoundException;
import com.saas.promotion.mapper.PromoteCandidateMapper;
import com.saas.promotion.model.PromoteCandidate;
import com.saas.promotion.model.Candidate;
import com.saas.promotion.repository.PromoteCandidateRepository;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromoteCandidateService {

    private final PromoteCandidateRepository promoteCandidateRepository;
    private final PromoteCandidateMapper promoteCandidateMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeServiceClient employeeServiceClient;

    @Transactional
    public PromoteCandidateResponse addPromoteCandidate(UUID tenantId,
                                                        PromoteCandidateRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenant.getId(), request.getCandidateId());
        if (promoteCandidateRepository
                .existsByTenantIdAndCandidateId(tenant.getId(), request.getCandidateId())) {
            throw new ResourceExistsException("Candidate already promoted");
        }
        EmployeeDto employee = validationUtil
                .getEmployeeById(tenant.getId(), candidate.getEmployeeId());
        RecruitmentDto recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), candidate.getRecruitmentId());
        PromoteCandidate promoteCandidate = promoteCandidateMapper.mapToEntity(tenant, request);
        promoteCandidate = promoteCandidateRepository.save(promoteCandidate);
        employeeServiceClient.addEmployeeHistory(tenant.getId(), employee.getId(),
                recruitment.getVacancyNumber(), request.getPayGradeId());
        return promoteCandidateMapper.mapToDto(promoteCandidate);
    }

    public List<PromoteCandidateResponse> getAllPromoteCandidates(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<PromoteCandidate> promoteCandidates = promoteCandidateRepository
                .findByTenantId(tenant.getId());
        return promoteCandidates.stream()
                .map(promoteCandidateMapper::mapToDto)
                .toList();
    }

    public PromoteCandidateResponse getPromoteCandidateById(UUID tenantId,
                                                            UUID promoteId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PromoteCandidate promoteCandidate = getPromoteCandidate(tenant.getId(), promoteId);
        return promoteCandidateMapper.mapToDto(promoteCandidate);
    }

    public PromoteCandidateResponse updatePromoteCandidate(UUID tenantId,
                                                           UUID promoteId,
                                                           UUID payGradeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PromoteCandidate promoteCandidate = getPromoteCandidate(tenant.getId(), promoteId);
        Candidate candidate = validationUtil
                .getCandidateById(tenant.getId(), promoteCandidate.getCandidate().getId());
        RecruitmentDto recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), candidate.getRecruitmentId());
        PayGradeDto payGrade = validationUtil
                .getPayGradeById(tenant.getId(), recruitment.getJobId(), payGradeId);
        if (payGradeId != null) {
            promoteCandidate.setPayGradeId(payGrade.getId());
        }
        promoteCandidate = promoteCandidateRepository.save(promoteCandidate);
        return promoteCandidateMapper.mapToDto(promoteCandidate);
    }

    public void deletePromoteCandidate(UUID tenantId,
                                       UUID promoteId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PromoteCandidate promoteCandidate = getPromoteCandidate(tenant.getId(), promoteId);
        promoteCandidateRepository.delete(promoteCandidate);
    }

    private PromoteCandidate getPromoteCandidate(UUID tenantId, UUID promoteId) {
        return promoteCandidateRepository.findById(promoteId)
                .filter(pc -> pc.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Promote candidate not found with id '" + promoteId + "'"));
    }
}
