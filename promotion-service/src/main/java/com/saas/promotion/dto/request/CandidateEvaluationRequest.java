package com.saas.promotion.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEvaluationRequest {

    @NotNull(message = "Result cannot be null")
    @Min(value = 0, message = "Weight must be greater than or equal to 0")
    private double result;

    @NotNull(message = "Promotion criteria cannot id be null")
    private UUID criteriaId;
}
