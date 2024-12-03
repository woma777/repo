package com.saas.planning.utility;

import com.saas.planning.enums.HrPlanningServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Annual Recruitment and Promotion Permissions */
    public void addAnnualRecruitmentAndPromotionPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.ADD_ANNUAL_RECRUITMENT_AND_PROMOTION);
    }

    public void getAllAnnualRecruitmentAndPromotionsPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.GET_ALL_ANNUAL_RECRUITMENT_AND_PROMOTIONS);
    }

    public void getAnnualRecruitmentAndPromotionByIdPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.GET_ANNUAL_RECRUITMENT_AND_PROMOTION_BY_ID);
    }

    public void updateAnnualRecruitmentAndPromotionPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.UPDATE_ANNUAL_RECRUITMENT_AND_PROMOTION);
    }

    public void deleteAnnualRecruitmentAndPromotionPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.DELETE_ANNUAL_RECRUITMENT_AND_PROMOTION);
    }

    /* HR Need Request Permissions */
    public void addHrNeedRequestPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.ADD_HR_NEED_REQUEST);
    }

    public void getAllHrNeedRequestsPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.GET_ALL_HR_NEED_REQUESTS);
    }

    public void getHrNeedRequestByIdPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.GET_HR_NEED_REQUEST_BY_ID);
    }

    public void getHrNeedRequestByStaffPlanIdPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.GET_HR_NEED_REQUEST_BY_STAFF_PLAN_ID);
    }

    public void updateHrNeedRequestPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.UPDATE_HR_NEED_REQUEST);
    }

    public void deleteHrNeedRequestPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.DELETE_HR_NEED_REQUEST);
    }

    /* HR Analysis Permissions */
    public void addHrAnalysisPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.ADD_HR_ANALYSIS);
    }

    public void getAllHrAnalysesPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.GET_ALL_HR_ANALYSES);
    }

    public void getHrAnalysisByIdPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.GET_HR_ANALYSIS_BY_ID);
    }

    public void updateHrAnalysisPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.UPDATE_HR_ANALYSIS);
    }

    public void deleteHrAnalysisPermission(UUID tenantId) {
        checkPermission(tenantId, HrPlanningServiceResourceName.DELETE_HR_ANALYSIS);
    }

    private void checkPermission(UUID tenantId, HrPlanningServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}