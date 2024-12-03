package com.saas.planning.enums;

import lombok.Getter;

@Getter
public enum HrPlanningServiceResourceName {

    /* Annual Recruitment and Promotion */
    ADD_ANNUAL_RECRUITMENT_AND_PROMOTION("Add Annual Recruitment and Promotion"),
    GET_ALL_ANNUAL_RECRUITMENT_AND_PROMOTIONS("Get All Annual Recruitment and Promotions"),
    GET_ANNUAL_RECRUITMENT_AND_PROMOTION_BY_ID("Get Annual Recruitment and Promotion Details"),
    UPDATE_ANNUAL_RECRUITMENT_AND_PROMOTION("Update Annual Recruitment and Promotion"),
    DELETE_ANNUAL_RECRUITMENT_AND_PROMOTION("Delete Annual Recruitment and Promotion"),

    /* HR Need Request */
    ADD_HR_NEED_REQUEST("Add HR Need Request"),
    GET_ALL_HR_NEED_REQUESTS("Get All HR Need Requests"),
    GET_HR_NEED_REQUEST_BY_ID("Get HR Need Request Details"),
    GET_HR_NEED_REQUEST_BY_STAFF_PLAN_ID("Get HR Need Request by Staff Plan"),
    UPDATE_HR_NEED_REQUEST("Update HR Need Request"),
    DELETE_HR_NEED_REQUEST("Delete HR Need Request"),

    /* HR Analysis */
    ADD_HR_ANALYSIS("Add HR Analysis"),
    GET_ALL_HR_ANALYSES("Get All HR Analyses"),
    GET_HR_ANALYSIS_BY_ID("Get HR Analysis Details"),
    UPDATE_HR_ANALYSIS("Update HR Analysis"),
    DELETE_HR_ANALYSIS("Delete HR Analysis");

    private final String value;

    HrPlanningServiceResourceName(String value) {
        this.value = value;
    }
}
