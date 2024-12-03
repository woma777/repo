package com.saas.recruitment.dto.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantRequest {

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    @Size(min = 2, max = 20, message = "Middle name must be between 2 and 20 characters")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Marital status cannot be null")
    private String maritalStatus;

    @NotNull(message = "Country ID cannot be null")
    private UUID countryId;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Phone number must be a valid phone number")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
    private String email;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Home telephone must be a valid phone number")
    @Size(max = 20, message = "Home telephone must be less than 20 characters")
    private String homeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Office telephone must be a valid phone number")
    @Size(max = 20, message = "Office telephone must be less than 20 characters")
    private String officeTelephone;

    @Size(max = 20, message = "House number must be less than 20 characters")
    private String houseNumber;

    @Size(max = 20, message = "PO Box must be less than 20 characters")
    private String poBox;

    @Size(max = 250, message = "Skills must be less than 250 characters")
    private String skills;

    @Size(max = 250, message = "Other information must be less than 250 characters")
    private String otherInformation;

    @Size(max = 250, message = "Hobbies must be less than 250 characters")
    private String hobbies;

    @NotNull(message = "Recruitment ID cannot be null")
    private UUID recruitmentId;
}
