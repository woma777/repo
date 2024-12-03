package com.saas.organization.utility;

import com.saas.organization.enums.OrganizationServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Tenant Permissions */
    public void getTenantByIdTenantPermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            permissionUtil.isTenantEmployee(tenantId);
        }
    }

    public void updateTenantPermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_TENANT);
        }
    }

    /* Work Unit Permissions */
    public void addWorkUnitPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_WORK_UNIT);
    }

    public void getAllWorkUnitsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_WORK_UNITS);
    }

    public void getWorkUnitByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_WORK_UNIT_BY_ID);
    }

    public void updateWorkUnitPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_WORK_UNIT);
    }

    public void deleteWorkUnitPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_WORK_UNIT);
    }

    /* Staff Plan Permissions */
    public void addStaffPlanPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_STAFF_PLAN);
    }

    public void getAllStaffPlansPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_STAFF_PLANS);
    }

    public void getStaffPlanByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_STAFF_PLAN_BY_ID);
    }

    public void getStaffPlansByDepartmentIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_STAFF_PLANS_BY_DEPARTMENT_ID);
    }

    public void updateStaffPlanPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_STAFF_PLAN);
    }

    public void deleteStaffPlanPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_STAFF_PLAN);
    }

    /* Qualification Permissions */
    public void addQualificationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_QUALIFICATION);
    }

    public void getAllQualificationsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_QUALIFICATIONS);
    }

    public void getQualificationByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_QUALIFICATION_BY_ID);
    }

    public void updateQualificationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_QUALIFICATION);
    }

    public void deleteQualificationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_QUALIFICATION);
    }

    /* Pay Grade Permissions */
    public void addPayGradePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_PAY_GRADE);
    }

    public void getAllPayGradesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_PAY_GRADES);
    }

    public void getPayGradeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_PAY_GRADE_BY_ID);
    }

    public void getPayGradesByJobGradeIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_PAY_GRADES_BY_JOB_GRADE_ID);
    }

    public void updatePayGradePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_PAY_GRADE);
    }

    public void deletePayGradePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_PAY_GRADE);
    }

    /* Location Permissions */
    public void addLocationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_LOCATION);
    }

    public void getAllLocationsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_LOCATIONS);
    }

    public void getLocationByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_LOCATION_BY_ID);
    }

    public void addSubLocationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_SUB_LOCATION);
    }

    public void getAllParentLocationsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_PARENT_LOCATIONS);
    }

    public void getAllSubLocationsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_SUB_LOCATIONS);
    }

    public void updateLocationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_LOCATION);
    }

    public void deleteSubLocationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_SUB_LOCATION);
    }

    public void deleteLocationPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_LOCATION);
    }

    /* Job Permissions */
    public void addJobPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_JOB);
    }

    public void getAllJobsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_JOBS);
    }

    public void getJobByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_JOB_BY_ID);
    }

    public void getJobsByDepartmentIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_JOBS_BY_DEPARTMENT_ID);
    }

    public void updateJobPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_JOB);
    }

    public void deleteJobPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_JOB);
    }

    /* Job Grade Permissions */
    public void addJobGradePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_JOB_GRADE);
    }

    public void getAllJobGradesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_JOB_GRADES);
    }

    public void getJobGradeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_JOB_GRADE_BY_ID);
    }

    public void updateJobGradePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_JOB_GRADE);
    }

    public void deleteJobGradePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_JOB_GRADE);
    }

    /* Job Category Permissions */
    public void addJobCategoryPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_JOB_CATEGORY);
    }

    public void getAllJobCategoriesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_JOB_CATEGORIES);
    }

    public void getJobCategoryByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_JOB_CATEGORY_BY_ID);
    }

    public void updateJobCategoryPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_JOB_CATEGORY);
    }

    public void deleteJobCategoryPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_JOB_CATEGORY);
    }

    /* Field of Study Permissions */
    public void addFieldOfStudyPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_FIELD_OF_STUDY);
    }

    public void getAllFieldOfStudiesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_FIELD_OF_STUDIES);
    }

    public void getFieldOfStudyByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_FIELD_OF_STUDY_BY_ID);
    }

    public void updateFieldOfStudyPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_FIELD_OF_STUDY);
    }

    public void deleteFieldOfStudyPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_FIELD_OF_STUDY);
    }

    /* Education Level Permissions */
    public void addEducationLevelPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_EDUCATION_LEVEL);
    }

    public void getAllEducationLevelsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_EDUCATION_LEVELS);
    }

    public void getEducationLevelByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_EDUCATION_LEVEL_BY_ID);
    }

    public void updateEducationLevelPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_EDUCATION_LEVEL);
    }

    public void deleteEducationLevelPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_EDUCATION_LEVEL);
    }

    /* Department Permissions */
    public void addDepartmentPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_DEPARTMENT);
    }

    public void getAllDepartmentsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_DEPARTMENTS);
    }

    public void getDepartmentByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_DEPARTMENT_BY_ID);
    }

    public void addSubDepartmentPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_SUB_DEPARTMENT);
    }

    public void transferParentDepartmentPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.TRANSFER_PARENT_DEPARTMENT);
    }

    public void transferSubDepartmentPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.TRANSFER_SUB_DEPARTMENT);
    }

    public void getAllParentDepartmentsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_PARENT_DEPARTMENTS);
    }

    public void getAllSubDepartmentsPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_SUB_DEPARTMENTS);
    }

    public void updateDepartmentPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_DEPARTMENT);
    }

    public void deleteSubDepartmentPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_SUB_DEPARTMENT);
    }

    public void deleteDepartmentPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_DEPARTMENT);
    }

    public void getAllDepartmentHistoriesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_DEPARTMENT_HISTORIES);
    }

    /* Department Type Permissions */
    public void addDepartmentTypePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_DEPARTMENT_TYPE);
    }

    public void getAllDepartmentTypesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_DEPARTMENT_TYPES);
    }

    public void getDepartmentTypeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_DEPARTMENT_TYPE_BY_ID);
    }

    public void updateDepartmentTypePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_DEPARTMENT_TYPE);
    }

    public void deleteDepartmentTypePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_DEPARTMENT_TYPE);
    }

    /* Address Permissions */
    public void addAddressPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_ADDRESS);
    }

    public void getAllAddressesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_ADDRESSES);
    }

    public void getAddressByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ADDRESS_BY_ID);
    }

    public void getAddressesByDepartmentIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ADDRESSES_BY_DEPARTMENT_ID);
    }

    public void updateAddressPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_ADDRESS);
    }

    public void deleteAddressPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_ADDRESS);
    }

    /* Location Type Permissions */
    public void addLocationTypePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.ADD_LOCATION_TYPE);
    }

    public void getAllLocationTypesPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_ALL_LOCATION_TYPES);
    }

    public void getLocationTypeByIdPermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.GET_LOCATION_TYPE_BY_ID);
    }

    public void updateLocationTypePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.UPDATE_LOCATION_TYPE);
    }

    public void deleteLocationTypePermission(UUID tenantId) {
        checkPermission(tenantId, OrganizationServiceResourceName.DELETE_LOCATION_TYPE);
    }

    private void checkPermission(UUID tenantId, OrganizationServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}