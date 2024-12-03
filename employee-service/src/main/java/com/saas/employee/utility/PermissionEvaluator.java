package com.saas.employee.utility;

import com.saas.employee.enums.EmployeeServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Employee Permissions */
    public void addEmployeePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.ADD_EMPLOYEE);
    }

    public void getAllEmployeesPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_ALL_EMPLOYEES);
    }

    public void getEmployeeByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_EMPLOYEE_BY_ID, employeeId);
    }

    public void getEmployeeByNamePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_EMPLOYEE_BY_NAME);
    }

    public void getEmployeeByEmployeeIdPermission(UUID tenantId, String employeeId) {
        boolean isEmployeeHimself = permissionUtil.isEmployeeHimself(tenantId, null, employeeId);
        if (!isEmployeeHimself) {
            checkPermission(tenantId, EmployeeServiceResourceName.GET_EMPLOYEE_BY_EMPLOYEE_ID);
        }
    }

    public void downloadEmployeeImagePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DOWNLOAD_EMPLOYEE_IMAGE, employeeId);
    }

    public void getEmployeesByDepartmentIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_EMPLOYEES_BY_DEPARTMENT_ID);
    }

    public void getEmployeesByEmploymentTypePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_EMPLOYEES_BY_EMPLOYMENT_TYPE);
    }

    public void getEmployeesByDutyStationPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_EMPLOYEES_BY_DUTY_STATION);
    }

    public void addEmployeeHistoryPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.ADD_EMPLOYEE_HISTORY);
    }

    public void getEmployeesHistoriesPermission(UUID tenantId, UUID employeeId) {
         doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_EMPLOYEE_HISTORIES, employeeId);
    }

    public void updateEmployeePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.UPDATE_EMPLOYEE);
    }

    public void updateEmployeeEmailPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_EMPLOYEE_EMAIL, employeeId);
    }

    public void deleteEmployeePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.DELETE_EMPLOYEE);
    }

    /* Employee Training or Certificate Permissions */
    public void addTrainingPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_TRAINING, employeeId);
    }

    public void getAllTrainingsPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_TRAININGS, employeeId);
    }

    public void getTrainingByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_TRAINING_BY_ID, employeeId);
    }

    public void getTrainingsByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_TRAININGS_BY_EMPLOYEE_ID);
    }

    public void downloadTrainingCertificatePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE, employeeId);
    }

    public void updateTrainingPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_TRAINING, employeeId);
    }

    public void deleteTrainingPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_TRAINING, employeeId);
    }

    /* Title Name Permissions */
    public void addTitleNamePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.ADD_TITLE_NAME);
    }

    public void getAllTitleNamesPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_ALL_TITLE_NAMES);
    }

    public void getTitleNameByIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_TITLE_NAME_BY_ID);
    }

    public void updateTitleNamePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.UPDATE_TITLE_NAME);
    }

    public void deleteTitleNamePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.DELETE_TITLE_NAME);
    }

    /* Employee Skill Permissions */
    public void addSkillPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_SKILL, employeeId);
    }

    public void getAllSkillsPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_SKILLS, employeeId);
    }

    public void getSkillByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_SKILL_BY_ID, employeeId);
    }

    public void getSkillsByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_SKILLS_BY_EMPLOYEE_ID);
    }

    public void updateSkillPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_SKILL, employeeId);
    }

    public void deleteSkillPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_SKILL, employeeId);
    }

    /* Employee Reference Permissions */
    public void addReferencePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_REFERENCE, employeeId);
    }

    public void getAllReferencesPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_REFERENCES, employeeId);
    }

    public void getReferenceByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_REFERENCE_BY_ID, employeeId);
    }

    public void getReferencesByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_REFERENCES_BY_EMPLOYEE_ID);
    }

    public void updateReferencePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_REFERENCE, employeeId);
    }

    public void deleteReferencePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_REFERENCE, employeeId);
    }

    /* Employee Language Permissions */
    public void addLanguagePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_LANGUAGE, employeeId);
    }

    public void getAllLanguagesPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_LANGUAGES, employeeId);
    }

    public void getLanguageByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_LANGUAGE_BY_ID, employeeId);
    }

    public void getLanguagesByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_LANGUAGES_BY_EMPLOYEE_ID);
    }

    public void updateLanguagePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_LANGUAGE, employeeId);
    }

    public void deleteLanguagePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_LANGUAGE, employeeId);
    }

    /* Language Name Permissions */
    public void addLanguageNamePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.ADD_LANGUAGE_NAME);
    }

    public void getAllLanguageNamesPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_ALL_LANGUAGE_NAMES);
    }

    public void getLanguageNameByIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_LANGUAGE_NAME_BY_ID);
    }

    public void updateLanguageNamePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.UPDATE_LANGUAGE_NAME);
    }

    public void deleteLanguageNamePermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.DELETE_LANGUAGE_NAME);
    }

    /* Employee Family Permissions */
    public void addFamilyPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_FAMILY, employeeId);
    }

    public void getAllFamiliesPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_FAMILIES, employeeId);
    }

    public void getFamilyByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_FAMILY_BY_ID, employeeId);
    }

    public void getFamiliesByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_FAMILIES_BY_EMPLOYEE_ID);
    }

    public void updateFamilyPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_FAMILY, employeeId);
    }

    public void deleteFamilyPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_FAMILY, employeeId);
    }

    /* Employee Experience Permissions */
    public void addExperiencePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_EXPERIENCE, employeeId);
    }

    public void getAllExperiencesPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_EXPERIENCES, employeeId);
    }

    public void getExperienceByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_EXPERIENCE_BY_ID, employeeId);
    }

    public void getExperiencesByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_EXPERIENCES_BY_EMPLOYEE_ID);
    }

    public void downloadExperienceDocumentPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT, employeeId);
    }

    public void updateExperiencePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_EXPERIENCE, employeeId);
    }

    public void deleteExperiencePermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_EXPERIENCE, employeeId);
    }

    /* Employee Education Permissions */
    public void addEducationPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_EDUCATION, employeeId);
    }

    public void getAllEducationsPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_EDUCATIONS, employeeId);
    }

    public void getEducationByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_EDUCATION_BY_ID, employeeId);
    }

    public void getEducationsByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_EDUCATIONS_BY_EMPLOYEE_ID);
    }

    public void downloadEducationDocumentPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT, employeeId);
    }

    public void updateEducationPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_EDUCATION, employeeId);
    }

    public void deleteEducationPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_EDUCATION, employeeId);
    }

    /* Employee Address Permissions */
    public void addAddressPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.ADD_ADDRESS, employeeId);
    }

    public void getAllAddressesPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ALL_ADDRESSES, employeeId);
    }

    public void getAddressByIdPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.GET_ADDRESS_BY_ID, employeeId);
    }

    public void getAddressesByEmployeeIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_ADDRESSES_BY_EMPLOYEE_ID);
    }

    public void updateAddressPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.UPDATE_ADDRESS, employeeId);
    }

    public void deleteAddressPermission(UUID tenantId, UUID employeeId) {
        doubleCheckPermission(tenantId, EmployeeServiceResourceName.DELETE_ADDRESS, employeeId);
    }

    /* Duty Station Permissions */
    public void addDutyStationPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.ADD_DUTY_STATION);
    }

    public void getAllDutyStationsPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_ALL_DUTY_STATIONS);
    }

    public void getDutyStationByIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_DUTY_STATION_BY_ID);
    }

    public void updateDutyStationPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.UPDATE_DUTY_STATION);
    }

    public void deleteDutyStationPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.DELETE_DUTY_STATION);
    }

    /* Country Permissions */
    public void addCountryPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.ADD_COUNTRY);
    }

    public void getAllCountriesPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_ALL_COUNTRIES);
    }

    public void getCountryByIdPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.GET_COUNTRY_BY_ID);
    }

    public void updateCountryPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.UPDATE_COUNTRY);
    }

    public void deleteCountryPermission(UUID tenantId) {
        checkPermission(tenantId, EmployeeServiceResourceName.DELETE_COUNTRY);
    }

    private void checkPermission(UUID tenantId, EmployeeServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }

    private void doubleCheckPermission(UUID tenantId,
                                            EmployeeServiceResourceName resourceName,
                                            UUID employeeId) {

        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if(!hasPermission) {
            permissionUtil.isEmployeeHimself(tenantId, employeeId, null);
        }
    }
}
