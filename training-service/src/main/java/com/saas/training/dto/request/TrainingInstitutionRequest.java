package com.saas.training.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingInstitutionRequest {

    @NotBlank(message = "Institution name cannot be blank")
    @Size(min = 2, max = 50, message = "Institution name must be between 2 and 50 characters")
    private String institutionName;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    @NotNull(message = "Cost per person cannot be null")
    @DecimalMin(value = "0.0", message = "Cost per person must be at least 0.0")
    private Double costPerPerson;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Phone number must be a valid phone number")
    @Size(max = 20, message = "Phone number must be less than 20 characters")
    private String phoneNumber;

    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 2, max = 30, message = "Email must be between 2 and 30 characters")
    private String email;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Fax must be a valid phone number")
    @Size(max = 20, message = "Fax must be less than 20 characters")
    private String fax;

    @Size(max = 30, message = "Website must be less than 30 characters")
    private String website;

    @Size(max = 20, message = "TIN number must be less than 20 characters")
    private String tinNumber;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;
}
