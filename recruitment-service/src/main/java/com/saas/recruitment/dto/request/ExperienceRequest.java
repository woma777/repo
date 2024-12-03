package com.saas.recruitment.dto.request;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRequest {

    @NotBlank(message = "Institution cannot be blank")
    @Size(min = 2, max = 100, message = "Institution must be between 2 and 100 characters")
    private String institution;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    @NotBlank(message = "Job title cannot be blank")
    @Size(min = 2, max = 100, message = "Job title must be between 2 and 100 characters")
    private String jobTitle;

    @NotNull(message = "Salary cannot be null")
    @DecimalMin(value = "0.0", message = "Salary must be greater than or equal to 0")
    private Double salary;

    @NotBlank(message = "Responsibility cannot be blank")
    @Size(min = 2, max = 100, message = "Responsibility must be between 2 and 100 characters")
    private String responsibility;

    @NotNull(message = "Experience type cannot be null")
    private String experienceType;

    @NotNull(message = "Start date cannot be null")
    @Past(message = "Start date must be a past date")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be a past or present date")
    private LocalDate endDate;

    @NotBlank(message = "Reason for termination cannot be blank")
    @Size(min = 2, max = 250, message = "Reason for termination must be between 2 and 250 characters")
    private String reasonForTermination;

    @AssertTrue(message = "End date must be after the start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return endDate.isAfter(startDate);
    }
}
