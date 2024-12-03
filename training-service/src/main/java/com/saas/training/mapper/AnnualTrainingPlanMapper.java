package com.saas.training.mapper;

import com.saas.training.dto.clientDto.BudgetYearDto;
import com.saas.training.dto.clientDto.DepartmentDto;
import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.AnnualTrainingPlanRequest;
import com.saas.training.dto.response.AnnualTrainingPlanResponse;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.AnnualTrainingPlan;
import com.saas.training.model.TrainingCourse;
import com.saas.training.model.TrainingCourseCategory;
import com.saas.training.model.TrainingInstitution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnualTrainingPlanMapper {

    private final ValidationUtil validationUtil;

    public AnnualTrainingPlan mapToEntity(TenantDto tenant,
                                          AnnualTrainingPlanRequest request) {

        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingCourse trainingCourse = validationUtil
                .getTrainingCourseById(tenant.getId(), request.getTrainingCourseId());
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), request.getTrainingInstitutionId());
        TrainingCourseCategory trainingCourseCategory = validationUtil
                .getCategoryById(tenant.getId(), request.getCourseCategoryId());

        AnnualTrainingPlan annualTrainingPlan = new AnnualTrainingPlan();
        annualTrainingPlan.setTenantId(tenant.getId());
        annualTrainingPlan.setDepartmentId(department.getId());
        annualTrainingPlan.setBudgetYearId(budgetYear.getId());
        annualTrainingPlan.setTrainingInstitution(trainingInstitution);
        annualTrainingPlan.setTrainingCourseCategory(trainingCourseCategory);
        annualTrainingPlan.setTrainingCourse(trainingCourse);
        annualTrainingPlan.setNumberOfParticipants(request.getNumberOfParticipants());
        annualTrainingPlan.setNumberOfDays(request.getNumberOfDays());
        annualTrainingPlan.setStartDate(request.getStartDate());
        annualTrainingPlan.setEndDate(request.getEndDate());
        annualTrainingPlan.setCostPerPerson(request.getCostPerPerson());
        annualTrainingPlan.setRound(request.getRound());
        annualTrainingPlan.setVenue(request.getVenue());
        annualTrainingPlan.setRemark(request.getRemark());

        return annualTrainingPlan;
    }

    public AnnualTrainingPlanResponse mapToDto(AnnualTrainingPlan annualTrainingPlan) {

        AnnualTrainingPlanResponse response = new AnnualTrainingPlanResponse();
        response.setId(annualTrainingPlan.getId());
        response.setDepartmentId(annualTrainingPlan.getDepartmentId());
        response.setBudgetYearId(annualTrainingPlan.getBudgetYearId());
        response.setCourseCategoryId(annualTrainingPlan.getTrainingCourseCategory().getId());
        response.setTrainingCourseId(annualTrainingPlan.getTrainingCourse().getId());
        response.setTrainingInstitutionId(annualTrainingPlan.getTrainingInstitution().getId());
        response.setNumberOfParticipants(annualTrainingPlan.getNumberOfParticipants());
        response.setNumberOfDays(annualTrainingPlan.getNumberOfDays());
        response.setStartDate(annualTrainingPlan.getStartDate());
        response.setEndDate(annualTrainingPlan.getEndDate());
        response.setCostPerPerson(annualTrainingPlan.getCostPerPerson());
        response.setRound(annualTrainingPlan.getRound());
        response.setVenue(annualTrainingPlan.getVenue());
        response.setRemark(annualTrainingPlan.getRemark());
        response.setTenantId(annualTrainingPlan.getTenantId());
        response.setCreatedAt(annualTrainingPlan.getCreatedAt());
        response.setUpdatedAt(annualTrainingPlan.getUpdatedAt());
        response.setCreatedBy(annualTrainingPlan.getCreatedBy());
        response.setUpdatedBy(annualTrainingPlan.getUpdatedBy());

        return response;
    }

    public AnnualTrainingPlan updateEntity(TenantDto tenant,
                                           AnnualTrainingPlan annualTrainingPlan,
                                           AnnualTrainingPlanRequest request) {

        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), request.getTrainingInstitutionId());
        TrainingCourseCategory trainingCourseCategory = validationUtil
                .getCategoryById(tenant.getId(), request.getCourseCategoryId());
        TrainingCourse trainingCourse = validationUtil
                .getTrainingCourseById(tenant.getId(), request.getTrainingCourseId());

        if (request.getDepartmentId() != null) {
            annualTrainingPlan.setDepartmentId(department.getId());
        }
        if (request.getBudgetYearId() != null) {
            annualTrainingPlan.setBudgetYearId(budgetYear.getId());
        }
        if (request.getTrainingInstitutionId() != null) {
            annualTrainingPlan.setTrainingInstitution(trainingInstitution);
        }
        if (request.getCourseCategoryId() != null) {
            annualTrainingPlan.setTrainingCourseCategory(trainingCourseCategory);
        }
        if (request.getTrainingCourseId() != null) {
            annualTrainingPlan.setTrainingCourse(trainingCourse);
        }
        if (request.getNumberOfParticipants() != null) {
            annualTrainingPlan.setNumberOfParticipants(request.getNumberOfParticipants());
        }
        if (request.getNumberOfDays() != null) {
            annualTrainingPlan.setNumberOfDays(request.getNumberOfDays());
        }
        if (request.getStartDate() != null) {
            annualTrainingPlan.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            annualTrainingPlan.setEndDate(request.getEndDate());
        }
        if (request.getCostPerPerson() != null) {
            annualTrainingPlan.setCostPerPerson(request.getCostPerPerson());
        }
        if (request.getRound() != null) {
            annualTrainingPlan.setRound(request.getRound());
        }
        if (request.getVenue() != null) {
            annualTrainingPlan.setVenue(request.getVenue());
        }
        if (request.getRemark() != null) {
            annualTrainingPlan.setRemark(request.getRemark());
        }

        return annualTrainingPlan;
    }
}
