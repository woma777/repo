package com.saas.promotion.enums;

import lombok.Getter;

@Getter
public enum PromotionServiceResourceName {

    /* Criteria Name */
    ADD_CRITERIA_NAME("Add Promotion Criteria Name"),
    GET_ALL_CRITERIA_NAMES("Get All Promotion Criteria Names"),
    GET_CRITERIA_NAME_BY_ID("Get Promotion Criteria Name Details"),
    UPDATE_CRITERIA_NAME("Update Promotion Criteria Name"),
    DELETE_CRITERIA_NAME("Delete Promotion Criteria Name"),

    /* Promotion Criteria */
    ADD_PROMOTION_CRITERIA("Add Promotion Criteria"),
    GET_ALL_PROMOTION_CRITERIA("Get All Promotion Criteria"),
    GET_PROMOTION_CRITERIA_BY_ID("Get Promotion Criteria Details"),
    UPDATE_PROMOTION_CRITERIA("Update Promotion Criteria"),
    DELETE_PROMOTION_CRITERIA("Delete Promotion Criteria"),

    /* Candidate */
    ADD_CANDIDATE("Add Promotion Candidate"),
    GET_ALL_CANDIDATES("Get All Promotion Candidates"),
    GET_CANDIDATE_BY_ID("Get Promotion Candidate Details"),
    UPDATE_CANDIDATE("Update Promotion Candidate"),
    DELETE_CANDIDATE("Delete Promotion Candidate"),

    /* Candidate Evaluation*/
    ADD_CANDIDATE_EVALUATION("Add Promotion Candidate Evaluation"),
    GET_ALL_CANDIDATE_EVALUATIONS("Get All Promotion Candidate Evaluations"),
    GET_CANDIDATE_EVALUATION_BY_ID("Get Promotion Candidate Evaluation Details"),
    UPDATE_CANDIDATE_EVALUATION("Update Promotion Candidate Evaluation"),
    DELETE_CANDIDATE_EVALUATION("Delete Promotion Candidate Evaluation"),

    /* Promote Candidate */
    ADD_PROMOTE_CANDIDATE("Add Promote Candidate"),
    GET_ALL_PROMOTE_CANDIDATES("Get All Promote Candidates"),
    GET_PROMOTE_CANDIDATE_BY_ID("Get Promote Candidate Details"),
    UPDATE_PROMOTE_CANDIDATE("Update Promote Candidate"),
    DELETE_PROMOTE_CANDIDATE("Delete Promote Candidate");

    private final String value;

    private PromotionServiceResourceName(String value) {
        this.value = value;
    }
}
