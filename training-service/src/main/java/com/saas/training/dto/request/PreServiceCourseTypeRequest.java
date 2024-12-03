package com.saas.training.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceCourseTypeRequest {

    @NotBlank(message = "Course name cannot be blank")
    @Size(min = 2, max = 50, message = "Course type must be between 2 and 50 characters")
    private String courseType;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;
}
