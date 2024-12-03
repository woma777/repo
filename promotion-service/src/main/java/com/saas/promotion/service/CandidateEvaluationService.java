package com.saas.promotion.service;

import com.saas.promotion.dto.clientDto.TenantDto;
import com.saas.promotion.dto.request.CandidateEvaluationRequest;
import com.saas.promotion.dto.response.CandidateEvaluationResponse;
import com.saas.promotion.exception.ResourceExistsException;
import com.saas.promotion.exception.ResourceNotFoundException;
import com.saas.promotion.mapper.CandidateEvaluationMapper;
import com.saas.promotion.model.CandidateEvaluation;
import com.saas.promotion.model.Candidate;
import com.saas.promotion.model.PromotionCriteria;
import com.saas.promotion.repository.CandidateEvaluationRepository;
import com.saas.promotion.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateEvaluationService {

    private final CandidateEvaluationRepository evaluationRepository;
    private final CandidateEvaluationMapper evaluationMapper;
    private final ValidationUtil validationUtil;

    public CandidateEvaluationResponse addCandidateEvaluation(UUID tenantId,
                                                              UUID candidateId,
                                                              CandidateEvaluationRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenant.getId(), candidateId);
        PromotionCriteria criteria = validationUtil
                .getPromotionCriteria(tenant.getId(), request.getCriteriaId());
        if (evaluationRepository.existsByTenantIdAndCandidateIdAndPromotionCriteriaId(
                tenantId, candidateId, request.getCriteriaId())) {
            throw new ResourceExistsException("Candidate evaluation already exists!");
        }
        validateResult(criteria, request.getResult());
        CandidateEvaluation evaluation = evaluationMapper.mapToEntity(tenant, candidate, request);
        evaluation = evaluationRepository.save(evaluation);
        return evaluationMapper.mapToDto(evaluation);
    }

    public List<CandidateEvaluationResponse> getAllCandidateEvaluations(UUID tenantId,
                                                                        UUID candidateId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenantId, candidateId);
        List<CandidateEvaluation> evaluations = evaluationRepository
                .findByTenantIdAndCandidateId(tenant.getId(), candidate.getId());
        return evaluations.stream()
                .map(evaluationMapper::mapToDto)
                .toList();
    }

    public  CandidateEvaluationResponse getCandidateEvaluationById(UUID tenantId,
                                                                   UUID candidateId,
                                                                   UUID evaluationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenantId, candidateId);
        CandidateEvaluation evaluation = getCandidateEvaluation(tenant, candidate, evaluationId);
        return evaluationMapper.mapToDto(evaluation);
    }

    public CandidateEvaluationResponse updateCandidateEvaluation(UUID tenantId,
                                                                 UUID candidateId,
                                                                 UUID evaluationId,
                                                                 double result) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenantId, candidateId);
        CandidateEvaluation evaluation = getCandidateEvaluation(tenant, candidate, evaluationId);
        PromotionCriteria criteria = validationUtil
                .getPromotionCriteria(tenant.getId(), evaluation.getPromotionCriteria().getId());
        validateResult(criteria, result);
        if (result > 0) {
            evaluation.setResult(result);
        }
        evaluation = evaluationRepository.save(evaluation);
        return evaluationMapper.mapToDto(evaluation);
    }

    public void deleteCandidateEvaluation(UUID tenantId,
                                          UUID candidateId,
                                          UUID evaluationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Candidate candidate = validationUtil
                .getCandidateById(tenantId, candidateId);
        CandidateEvaluation evaluation = getCandidateEvaluation(tenant, candidate, evaluationId);
        evaluationRepository.delete(evaluation);
    }

    private CandidateEvaluation getCandidateEvaluation(TenantDto tenant,
                                                       Candidate candidate,
                                                       UUID evaluationId) {

        return evaluationRepository.findById(evaluationId)
                .filter(ce -> ce.getTenantId().equals(tenant.getId()))
                .filter(ce -> ce.getCandidate().equals(candidate))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate evaluation not found with id '" + evaluationId + "'"));
    }

    public void validateResult(PromotionCriteria promotionCriteria, double result) {
        if (promotionCriteria != null && result > promotionCriteria.getWeight()) {
            throw new IllegalArgumentException(
                    "Result must be less than or equal to " + promotionCriteria.getWeight());
        }
    }
}
