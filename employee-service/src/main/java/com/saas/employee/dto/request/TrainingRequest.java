package com.saas.employee.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest {

    @NotBlank(message = "Training title cannot be blank")
    @Size(min = 2, max = 250, message = "Training title must be between 2 and 250 characters")
    private String trainingTitle;

    @NotBlank(message = "Institution cannot be blank")
    @Size(min = 2, max = 250, message = "Institution must be between 2 and 250 characters")
    private String institution;

    @NotBlank(message = "Sponsored by cannot be blank")
    @Size(min = 2, max = 250, message = "Sponsored by must be between 2 and 250 characters")
    private String sponsoredBy;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be a past or present date")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be a past or present date")
    private LocalDate endDate;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !endDate.isBefore(startDate);
    }
}
