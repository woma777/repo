package com.saas.recruitment.dto.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortlistCriteriaRequest {

    @NotBlank(message = "Criteria name cannot be blank")
    @Size(min = 2, max = 30, message = "Criteria name must be between 2 and 30 characters")
    private String criteriaName;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Education level ID cannot be null")
    private UUID educationLevelId;

    @NotBlank(message = "Training or certificate cannot be blank")
    @Size(min = 2, max = 250, message = "Training or certificate must be between 2 and 250 characters")
    private String trainingOrCertificate;

    @NotNull(message = "Experience type cannot be null")
    private String experienceType;

    @DecimalMin(value = "0.0", message = "CGPA must be at least 0.0")
    @DecimalMax(value = "100.0", message = "CGPA must be at most 100.0")
    private Double CGPA;

    @NotNull(message = "Minimum experience cannot be null")
    private Integer minimumExperience;

    @NotNull(message = "Minimum age cannot be null")
    private Integer minimumAge;

    @NotNull(message = "Maximum age cannot be null")
    private Integer maximumAge;

    @Size(max = 100, message = "Other information must be less than 100 characters")
    private String other;

    @NotNull(message = "Recruitment cannot be null")
    private UUID recruitmentId;
}
