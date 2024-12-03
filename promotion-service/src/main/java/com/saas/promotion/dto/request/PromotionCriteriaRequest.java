package com.saas.promotion.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionCriteriaRequest {

    @NotNull(message = "Criteria name ID cannot be null")
    private UUID criteriaNameId;

    @NotNull(message = "Weight cannot be null")
    @Min(value = 0, message = "Weight must be greater than or equal to 0")
    @Max(value = 100, message = "Weight must be less than or equal to 100")
    private double weight;
}
