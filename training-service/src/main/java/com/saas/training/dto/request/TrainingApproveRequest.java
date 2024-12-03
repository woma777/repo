package com.saas.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingApproveRequest {

    @NotBlank(message = "Decision cannot be blank")
    private String decision;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;
}
