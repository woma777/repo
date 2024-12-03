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
public class PromoteCandidateResponse extends BaseResponse {

    private UUID payGradeId;
    private CandidateResponse candidate;
}
