package com.saas.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceCourseRequest {

    @NotBlank(message = "Course name cannot be blank")
    @Size(min = 2, max = 50, message = "Course name must be between 2 and 50 characters")
    private String courseName;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    @NotNull(message = "Course type cannot be null")
    private UUID courseTypeId;
}
