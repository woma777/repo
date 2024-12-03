package com.saas.promotion.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {

    @NotNull(message = "Vacancy number cannot be null")
    private String vacancyNumber;

    @NotNull(message = "Employee Id cannot be null")
    private String employeeId;
}
