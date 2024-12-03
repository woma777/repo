package com.saas.employee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Abbreviated name cannot be blank")
    @Size(min = 2, max = 10, message = "Abbreviated name must be between 2 and 10 characters")
    private String abbreviatedName;

    @NotBlank(message = "Code cannot be blank")
    @Size(min = 2, max = 10, message = "Code must be between 2 and 10 characters")
    private String code;
}
