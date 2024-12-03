package com.saas.promotion.mapper;

import com.saas.promotion.dto.clientDto.PayGradeDto;
import com.saas.promotion.dto.clientDto.RecruitmentDto;
import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.PromoteCandidateRequest;
import com.saas.promotion.dto.response.PromoteCandidateResponse;
import com.saas.promotion.model.Candidate;
import com.saas.promotion.model.PromoteCandidate;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromoteCandidateMapper {

    private final CandidateMapper candidateMapper;
    private final ValidationUtil validationUtil;

    public PromoteCandidate mapToEntity(TenantDto tenant,
                                        PromoteCandidateRequest request) {

        Candidate candidate = validationUtil
                .getCandidateById(tenant.getId(), request.getCandidateId());
        RecruitmentDto recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), candidate.getRecruitmentId());
        PayGradeDto payGrade = validationUtil
                .getPayGradeById(tenant.getId(), recruitment.getJobId(), request.getPayGradeId());

        PromoteCandidate promoteCandidate = new PromoteCandidate();
        promoteCandidate.setTenantId(tenant.getId());
        promoteCandidate.setPayGradeId(payGrade.getId());
        promoteCandidate.setCandidate(candidate);

        return promoteCandidate;
    }

    public PromoteCandidateResponse mapToDto(PromoteCandidate promoteCandidate) {

        PromoteCandidateResponse response = new PromoteCandidateResponse();
        response.setId(promoteCandidate.getId());
        response.setPayGradeId(promoteCandidate.getPayGradeId());
        response.setCandidate(candidateMapper
                .mapToDto(promoteCandidate.getCandidate()));
        response.setTenantId(promoteCandidate.getTenantId());
        response.setCreatedAt(promoteCandidate.getCreatedAt());
        response.setUpdatedAt(promoteCandidate.getUpdatedAt());
        response.setCreatedBy(promoteCandidate.getCreatedBy());
        response.setUpdatedBy(promoteCandidate.getUpdatedBy());

        return response;
    }
}
