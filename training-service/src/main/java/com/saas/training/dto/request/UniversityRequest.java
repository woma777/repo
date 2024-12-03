package com.saas.training.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityRequest {

    @NotBlank(message = "University name cannot be blank")
    @Size(min = 2, max = 30, message = "University name must be between 2 and 30 characters")
    private String universityName;

    @NotBlank(message = "Abbreviated name cannot be blank")
    @Size(min = 2, max = 10, message = "Abbreviated name must be between 2 and 10 characters")
    private String abbreviatedName;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    @NotNull(message = "Cost per person cannot be null")
    @DecimalMin(value = "0.0", message = "Cost per person must be at least 0.0")
    private Double costPerPerson;

    @NotBlank(message = "Mobile phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Mobile phone number must be a valid phone number")
    @Size(max = 20, message = "Mobile phone number must be less than 20 characters")
    private String mobilePhoneNumber;

    @NotBlank(message = "Telephone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Telephone number must be a valid phone number")
    @Size(max = 20, message = "Telephone number must be less than 20 characters")
    private String telephoneNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Size(min = 2, max = 30, message = "Email must be between 2 and 30 characters")
    private String email;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Fax must be a valid phone number")
    @Size(max = 20, message = "Fax must be less than 20 characters")
    private String fax;

    @Size(max = 30, message = "Website must be less than 30 characters")
    private String website;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;
}
