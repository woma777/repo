package com.saas.promotion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionCriteriaResponse extends BaseResponse {

    private CriteriaNameResponse criteriaName;
    private double weight;
    private UUID parentCriteriaId;
}
