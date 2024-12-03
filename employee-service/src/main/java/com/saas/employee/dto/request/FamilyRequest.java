package com.saas.employee.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRequest {

    @NotBlank(message = "Relationship type cannot be null")
    private String relationshipType;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 15, message = "First name must be between 2 and 15 characters")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    @Size(min = 2, max = 15, message = "Middle name must be between 2 and 15 characters")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 15, message = "Last name must be between 2 and 15 characters")
    private String lastName;

    @NotBlank(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    @Size(max = 20, message = "House number must be lee than 20 characters")
    private String houseNumber;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Home telephone must be a valid phone number")
    @Size(max = 20, message = "Home telephone must be less than 20 characters")
    private String homeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Office telephone must be a valid phone number")
    @Size(max = 20, message = "Office Telephone must be less than 20 characters")
    private String officeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Mobile number must be a valid phone number")
    @Size(max = 20, message = "Mobile number must be less than 20 characters")
    private String mobileNumber;

    @Email(message = "Email must be a valid email address")
    @Size(max = 50, message = "Email must be less than 50 characters")
    private String email;

    @Size(max = 20, message = "PO Box must be less than 20 characters")
    private String poBox;

    @NotNull(message = "Emergency contact cannot be null")
    private boolean isEmergencyContact;

    @AssertTrue(message = "Date of birth must be a valid past date")
    private boolean isDateOfBirthValid() {
        return dateOfBirth != null && dateOfBirth.isBefore(LocalDate.now());
    }
}
