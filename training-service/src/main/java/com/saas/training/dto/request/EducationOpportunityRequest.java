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
public class EducationOpportunityRequest {

    @NotNull(message = "Budget year ID cannot be null")
    private UUID budgetYearId;

    @NotNull(message = "Training location cannot be null")
    private String trainingLocation;

    @NotNull(message = "Country ID cannot be null")
    private UUID countryId;

    @NotNull(message = "Education level ID cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Qualification ID cannot be null")
    private UUID qualificationId;

    @NotBlank(message = "Sponsor cannot be blank")
    @Size(min = 2, max = 50, message = "Sponsor must be between 2 and 50 characters")
    private String sponsor;

    @NotBlank(message = "Institution cannot be blank")
    @Size(min = 2, max = 50, message = "Institution must be between 2 and 50 characters")
    private String institution;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be a present or future date")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be a present or future date")
    private LocalDate endDate;

    @NotNull(message = "Letter date cannot be null")
    @PastOrPresent(message = "Letter date must be a past or present date")
    private LocalDate letterDate;

    @NotBlank(message = "Letter reference number cannot be blank")
    @Size(min = 2, max = 20, message = "Letter reference number must be between 2 and 20 characters")
    private String letterReferenceNumber;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;

    @NotNull(message = "Employee ID cannot be null")
    private String employeeId;

    @NotNull(message = "Total result cannot be null")
    private Double totalResult;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);

    }
}
