package com.saas.promotion.mapper;

import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.CandidateEvaluationRequest;
import com.saas.promotion.dto.response.CandidateEvaluationResponse;
import com.saas.promotion.model.CandidateEvaluation;
import com.saas.promotion.model.Candidate;
import com.saas.promotion.model.PromotionCriteria;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidateEvaluationMapper {

    private final ValidationUtil validationUtil;

    public CandidateEvaluation mapToEntity(TenantDto tenant,
                                           Candidate candidate,
                                           CandidateEvaluationRequest request) {

        PromotionCriteria criteria = validationUtil
                .getPromotionCriteria(tenant.getId(), request.getCriteriaId());

        CandidateEvaluation candidateEvaluation = new CandidateEvaluation();
        candidateEvaluation.setTenantId(tenant.getId());
        candidateEvaluation.setCandidate(candidate);
        candidateEvaluation.setPromotionCriteria(criteria);
        candidateEvaluation.setResult(request.getResult());

        return candidateEvaluation;
    }

    public CandidateEvaluationResponse mapToDto(CandidateEvaluation candidateEvaluation) {

        CandidateEvaluationResponse response = new CandidateEvaluationResponse();
        response.setId(candidateEvaluation.getId());
        response.setResult(candidateEvaluation.getResult());
        response.setCandidateId(candidateEvaluation.getCandidate().getId());
        response.setCriteriaId(candidateEvaluation.getPromotionCriteria().getId());
        response.setTenantId(candidateEvaluation.getTenantId());
        response.setCreatedAt(candidateEvaluation.getCreatedAt());
        response.setUpdatedAt(candidateEvaluation.getUpdatedAt());
        response.setCreatedBy(candidateEvaluation.getCreatedBy());
        response.setUpdatedBy(candidateEvaluation.getUpdatedBy());

        return response;
    }
}
