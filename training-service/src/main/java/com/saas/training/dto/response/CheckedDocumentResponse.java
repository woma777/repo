package com.saas.training.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDocumentResponse extends BaseResponse {

    @NotBlank(message = "Document name cannot be blank")
    private String documentName;

    private String description;
}
