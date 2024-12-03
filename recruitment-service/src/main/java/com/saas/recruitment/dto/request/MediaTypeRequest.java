package com.saas.recruitment.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaTypeRequest {

    @NotBlank(message = "Media type name cannot be blank")
    @Size(min = 2, max = 30, message = "Media type name must be between 2 and 30 characters")
    private String mediaTypeName;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;
}
