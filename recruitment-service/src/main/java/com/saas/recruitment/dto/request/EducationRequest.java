package com.saas.recruitment.dto.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationRequest {

    @NotNull(message = "Education level ID cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Education type cannot be null")
    private String educationType;

    @NotNull(message = "Field of study ID cannot be null")
    private UUID fieldOfStudyId;

    @NotBlank(message = "Institution cannot be blank")
    @Size(min = 2, max = 100, message = "Institution must be between 2 and 100 characters")
    private String institution;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be a past or present date")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be a past or present date")
    private LocalDate endDate;

    @DecimalMin(value = "0.0", message = "Result must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Result must be less than or equal to 100")
    private Double result;

    @NotNull(message = "Applicant ID cannot be null")
    private UUID applicantId;

    @AssertTrue(message = "End date must be after the start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return endDate.isAfter(startDate);
    }
}
