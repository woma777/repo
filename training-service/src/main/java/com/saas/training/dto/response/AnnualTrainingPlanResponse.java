package com.saas.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualTrainingPlanResponse extends BaseResponse{

    private UUID departmentId;
    private UUID budgetYearId;
    private Integer numberOfParticipants;
    private Integer numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double costPerPerson;
    private Integer round;
    private String venue;
    private UUID courseCategoryId;
    private UUID trainingCourseId;
    private UUID trainingInstitutionId;
    private String remark;
}
