package com.saas.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillResponse extends BaseResponse {
    private String skillType;
    private String skillLevel;
    private String description;
    private UUID employeeId;
}
