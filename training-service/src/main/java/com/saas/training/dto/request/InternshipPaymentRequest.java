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
public class InternshipPaymentRequest {

    @NotBlank(message = "Reference letter cannot be blank")
    @Size(min = 2, max = 20, message = "Reference letter must be between 2 and 20 characters")
    private String referenceLetter;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "Payment amount cannot be null")
    @DecimalMin(value = "0.0", message = "Payment amount must be at least 0.0")
    private Double paymentAmount;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;

    @NotNull(message = "Internship student ID cannot be null")
    private UUID internId;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}

