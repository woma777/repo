package com.saas.training.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest {

    @NotNull(message = "Training type cannot be null")
    private String trainingType;

    @NotNull(message = "Department ID cannot be null")
    private UUID departmentId;

    @NotNull(message = "Budget year ID cannot be null")
    private UUID budgetYearId;

    @NotNull(message = "Number of participants cannot be null")
    @Min(value = 1, message = "Number of participants must be at least 1")
    private Integer numberOfParticipants;

    @NotNull(message = "Number of days cannot be null")
    @Min(value = 1, message = "Number of days must be at least 1")
    private Integer numberOfDays;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be a present or future date")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date must be a present or future date")
    private LocalDate endDate;

    @NotBlank(message = "Venue cannot be blank")
    @Size(min = 2, max = 100, message = "Venue must be between 2 and 100 characters")
    private String venue;

    @NotNull(message = "Cost per person cannot be null")
    @DecimalMin(value = "0.0", message = "Cost per person must be at least 0.0")
    private Double costPerPerson;

    @NotBlank(message = "Sponsored by cannot be blank")
    @Size(min = 2, max = 50, message = "Sponsored by must be between 2 and 50 characters")
    private String sponsoredBy;

    @NotNull(message = "Training location cannot be null")
    private String trainingLocation;

    @NotBlank(message = "Reason cannot be blank")
    @Size(min = 2, max = 100, message = "Reason must be between 2 and 100 characters")
    private String reason;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;

    @NotNull(message = "Course category id cannot be null")
    private UUID courseCategoryId;

    @NotNull(message = "AnnualTraining course id cannot be null")
    private UUID trainingCourseId;

    @NotNull(message = "training institution id cannot be null")
    private UUID trainingInstitutionId;

    @AssertTrue(message = "End date must be equal to or greater than the start date" +
            " plus the number of days minus one")
    private boolean isEndDateValid() {
        if (endDate == null) {
            return false;
        }
        return endDate.isEqual(startDate.plusDays(numberOfDays - 1)) ||
                endDate.isAfter(startDate.plusDays(numberOfDays - 1));
    }
}
