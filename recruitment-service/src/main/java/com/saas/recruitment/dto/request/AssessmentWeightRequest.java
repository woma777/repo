package com.saas.recruitment.dto.request;
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
public class AssessmentWeightRequest {

    @NotNull(message = "Written exam score cannot be null")
    @Min(value = 0, message = "Written exam score must be at least 0")
    @Max(value = 100, message = "Written exam score must be at most 100")
    private Double writtenExam;

    @NotNull(message = "Interview score cannot be null")
    @Min(value = 0, message = "Interview score must be at least 0")
    @Max(value = 100, message = "Interview score must be at most 100")
    private Double interview;

    @NotNull(message = "CGPA cannot be null")
    @Min(value = 0, message = "CGPA must be at least 0")
    @Max(value = 100, message = "CGPA must be at most 100")
    private Double CGPA;

    @NotNull(message = "Experience score cannot be null")
    @Min(value = 0, message = "Experience score must be at least 0")
    @Max(value = 100, message = "Experience score must be at most 100")
    private Double experience;

    @NotNull(message = "Practical exam score cannot be null")
    @Min(value = 0, message = "Practical exam score must be at least 0")
    @Max(value = 100, message = "Practical exam score must be at most 100")
    private Double practicalExam;

    @NotNull(message = "Other score cannot be null")
    @Min(value = 0, message = "Other score must be at least 0")
    @Max(value = 100, message = "Other score must be at most 100")
    private Double other;

    @NotNull(message = "Recruitment cannot be null")
    private UUID recruitmentId;
}
