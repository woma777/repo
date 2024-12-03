package com.saas.promotion.utility;

import com.saas.promotion.enums.PromotionServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Promotion Criteria Name Permission */
    public void addCriteriaNamePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.ADD_CRITERIA_NAME);
    }

    public void getAllCriteriaNamesPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_ALL_CRITERIA_NAMES);
    }

    public void getCriteriaNameByIdPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_CRITERIA_NAME_BY_ID);
    }

    public void updateCriteriaNamePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.UPDATE_CRITERIA_NAME);
    }

    public void deleteCriteriaNamePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.DELETE_CRITERIA_NAME);
    }

    /* Promotion Criteria  Permission */
    public void addPromotionCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.ADD_PROMOTION_CRITERIA);
    }

    public void getAllPromotionCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_ALL_PROMOTION_CRITERIA);
    }

    public void getPromotionCriteriaByIdPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_PROMOTION_CRITERIA_BY_ID);
    }

    public void updatePromotionCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.UPDATE_PROMOTION_CRITERIA);
    }

    public void deletePromotionCriteriaPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.DELETE_PROMOTION_CRITERIA);
    }

    /* Candidate  Permission */
    public void addCandidatePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.ADD_CANDIDATE);
    }

    public void getAllCandidatesPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_ALL_CANDIDATES);
    }

    public void getCandidateByIdPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_CANDIDATE_BY_ID);
    }

    public void updateCandidatePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.UPDATE_CANDIDATE);
    }

    public void deleteCandidatePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.DELETE_CANDIDATE);
    }

    /* Candidate Evaluation Permission */
    public void addCandidateEvaluationPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.ADD_CANDIDATE_EVALUATION);
    }

    public void getAllCandidateEvaluationsPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_ALL_CANDIDATE_EVALUATIONS);
    }

    public void getCandidateEvaluationByIdPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_CANDIDATE_EVALUATION_BY_ID);
    }

    public void updateCandidateEvaluationPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.UPDATE_CANDIDATE_EVALUATION);
    }

    public void deleteCandidateEvaluationPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.DELETE_CANDIDATE_EVALUATION);
    }

    /* Promote Candidate  Permission */
    public void addPromoteCandidatePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.ADD_PROMOTE_CANDIDATE);
    }

    public void getAllPromoteCandidatesPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_ALL_PROMOTE_CANDIDATES);
    }

    public void getPromoteCandidateByIdPermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.GET_PROMOTE_CANDIDATE_BY_ID);
    }

    public void updatePromoteCandidatePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.UPDATE_PROMOTE_CANDIDATE);
    }

    public void deletePromoteCandidatePermission(UUID tenantId) {
        checkPermission(tenantId, PromotionServiceResourceName.DELETE_PROMOTE_CANDIDATE);
    }

    private void checkPermission(UUID tenantId, PromotionServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
