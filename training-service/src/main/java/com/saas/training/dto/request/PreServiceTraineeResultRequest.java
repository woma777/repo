package com.saas.training.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTraineeResultRequest {

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be a past or present date")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be a past or present date")
    private LocalDate endDate;

    @NotNull(message = "Semester cannot be null")
    private String semester;

    @NotNull(message = "Result cannot be null")
    @DecimalMin(value = "0.0", message = "Result must be greater than or equal to 0")
    private Double result;

    @NotNull(message = "Decision cannot be null")
    private String decision;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);

    }
}
