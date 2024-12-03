package com.saas.training.mapper;

import com.saas.training.dto.clientDto.*;
import com.saas.training.dto.request.EducationOpportunityRequest;
import com.saas.training.dto.response.EducationOpportunityResponse;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.EducationOpportunity;
import com.saas.training.enums.TrainingLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EducationOpportunityMapper {

    private final ValidationUtil validationUtil;

    public EducationOpportunity mapToEntity(TenantDto tenant,
                                            EmployeeDto employee,
                                            EducationOpportunityRequest request) {

        QualificationDto qualification = validationUtil
                .getQualificationById(tenant.getId(), request.getQualificationId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());

        EducationOpportunity educationOpportunity = new EducationOpportunity();
        educationOpportunity.setTenantId(tenant.getId());
        educationOpportunity.setBudgetYearId(budgetYear.getId());
        educationOpportunity.setEmployeeId(employee.getId());
        educationOpportunity.setQualificationId(qualification.getId());
        educationOpportunity.setEducationLevelId(educationLevel.getId());
        educationOpportunity.setTrainingLocation(
                TrainingLocation.valueOf(request.getTrainingLocation().toUpperCase()));
        educationOpportunity.setCountryId(request.getCountryId());
        educationOpportunity.setSponsor(request.getSponsor());
        educationOpportunity.setInstitution(request.getInstitution());
        educationOpportunity.setStartDate(request.getStartDate());
        educationOpportunity.setEndDate(request.getEndDate());
        educationOpportunity.setLetterDate(request.getLetterDate());
        educationOpportunity.setLetterReferenceNumber(request.getLetterReferenceNumber());
        educationOpportunity.setRemark(request.getRemark());
        educationOpportunity.setTotalResult(request.getTotalResult());

        return educationOpportunity;
    }

    public EducationOpportunityResponse mapToDto(EducationOpportunity educationOpportunity) {

        EducationOpportunityResponse response = new EducationOpportunityResponse();
        response.setId(educationOpportunity.getId());
        response.setEmployeeId(educationOpportunity.getEmployeeId());
        response.setBudgetYearId(educationOpportunity.getBudgetYearId());
        response.setEducationLevelId(educationOpportunity.getEducationLevelId());
        response.setQualificationId(educationOpportunity.getQualificationId());
        response.setTrainingLocation(educationOpportunity.getTrainingLocation().getTrainingLocation());
        response.setCountryId(educationOpportunity.getCountryId());
        response.setSponsor(educationOpportunity.getSponsor());
        response.setInstitution(educationOpportunity.getInstitution());
        response.setStartDate(educationOpportunity.getStartDate());
        response.setEndDate(educationOpportunity.getEndDate());
        response.setLetterDate(educationOpportunity.getLetterDate());
        response.setLetterReferenceNumber(educationOpportunity.getLetterReferenceNumber());
        response.setRemark(educationOpportunity.getRemark());
        response.setTotalResult(educationOpportunity.getTotalResult());
        response.setTenantId(educationOpportunity.getTenantId());
        response.setCreatedAt(educationOpportunity.getCreatedAt());
        response.setUpdatedAt(educationOpportunity.getUpdatedAt());
        response.setCreatedBy(educationOpportunity.getCreatedBy());
        response.setUpdatedBy(educationOpportunity.getUpdatedBy());

        return response;
    }

    public EducationOpportunity updateEntity(TenantDto tenant,
                                             EmployeeDto employee,
                                             EducationOpportunity educationOpportunity,
                                             EducationOpportunityRequest request) {

        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        QualificationDto qualification = validationUtil
                .getQualificationById(tenant.getId(), request.getQualificationId());
        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());

        if (request.getBudgetYearId() != null) {
            educationOpportunity.setBudgetYearId(budgetYear.getId());
        }
        if (request.getEmployeeId() != null) {
            educationOpportunity.setEmployeeId(employee.getId());
        }
        if (request.getQualificationId() != null) {
            educationOpportunity.setQualificationId(qualification.getId());
        }
        if (request.getEducationLevelId() != null) {
            educationOpportunity.setEducationLevelId(educationLevel.getId());
        }
        if (request.getTrainingLocation() != null) {
            educationOpportunity.setTrainingLocation(
                    TrainingLocation.valueOf(request.getTrainingLocation().toUpperCase()));
        }
        if (request.getCountryId() != null) {
            educationOpportunity.setCountryId(request.getCountryId());
        }
        if (request.getSponsor() != null) {
            educationOpportunity.setSponsor(request.getSponsor());
        }
        if (request.getInstitution() != null) {
            educationOpportunity.setInstitution(request.getInstitution());
        }
        if (request.getStartDate() != null) {
            educationOpportunity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            educationOpportunity.setEndDate(request.getEndDate());
        }
        if (request.getLetterDate() != null) {
            educationOpportunity.setLetterDate(request.getLetterDate());
        }
        if (request.getLetterReferenceNumber() != null) {
            educationOpportunity.setLetterReferenceNumber(request.getLetterReferenceNumber());
        }
        if (request.getRemark() != null) {
            educationOpportunity.setRemark(request.getRemark());
        }
        if (request.getTotalResult() != null) {
            educationOpportunity.setTotalResult(request.getTotalResult());
        }

        return educationOpportunity;
    }
}
