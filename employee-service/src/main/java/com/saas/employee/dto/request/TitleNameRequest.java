package com.saas.employee.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleNameRequest {

    @NotBlank(message = "Title name cannot be blank")
    @Size(min = 2, max = 20, message = "Title name must be between 2 and 20 characters")
    private String titleName;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;
}
