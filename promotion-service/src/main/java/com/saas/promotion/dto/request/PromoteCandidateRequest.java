package com.saas.promotion.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoteCandidateRequest {

    @NotNull(message = "Pay grade Id cannot be null")
    private UUID payGradeId;

    @NotNull(message = "Candidate Id cannot be null")
    private UUID candidateId;
}
