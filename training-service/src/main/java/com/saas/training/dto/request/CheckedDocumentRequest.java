package com.saas.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDocumentRequest {

    @NotBlank(message = "Document name cannot be blank")
    @Size(min = 2, max = 30, message = "Document name must be between 2 and 30 characters")
    private String documentName;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;
}
