package com.saas.employee.dto.clientDto;

import com.saas.employee.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto extends BaseResponse {
    private UUID id;
    private String jobTitle;
    private String jobCode;
    private String reportsTo;
    private String jobType;
    private Integer minExperience;
    private String duties;
    private String language;
    private String skills;
    private String description;
    private String alternativeExperience;
    private String relativeExperience;
    private UUID educationLevelId;
    private UUID jobCategoryId;
    private UUID jobGradeId;
    private UUID workUnitId;
    private UUID qualificationId;
}
