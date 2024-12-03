package com.saas.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTraineeRequest {

    @NotBlank(message = "Trainee ID cannot be blank")
    @Size(min = 2, max = 10, message = "Trainee ID must be between 2 and 10 characters")
    private String traineeId;

    @NotNull(message = "Budget year ID cannot be null")
    private UUID budgetYearId;

    @NotBlank(message = "Batch code cannot be blank")
    @Size(min = 2, max = 15, message = "Batch code must be between 2 and 15 characters")
    private String batchCode;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    @Size(min = 2, max = 20, message = "Middle name must be between 2 and 20 characters")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotBlank(message = "Amharic first name cannot be blank")
    @Size(min = 2, max = 20, message = "Amharic first name must be between 2 and 20 characters")
    private String amharicFirstName;

    @NotBlank(message = "Amharic middle name cannot be blank")
    @Size(min = 2, max = 20, message = "Amharic middle name must be between 2 and 20 characters")
    private String amharicMiddleName;

    @NotBlank(message = "Amharic last name cannot be blank")
    @Size(min = 2, max = 20, message = "Amharic last name must be between 2 and 20 characters")
    private String amharicLastName;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Telephone number must be a valid phone number")
    @Size(max = 20, message = "Telephone number must be less than 20 characters")
    private String telephoneNumber;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Mobile number must be a valid phone number")
    @Size(min = 10, max = 20, message = "Mobile number must be between 10 and 20 characters")
    private String mobileNumber;

    @NotNull(message = "Education level ID cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Field of study ID cannot be null")
    private UUID fieldOfStudyId;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;

    private Set<UUID> documentIds = new HashSet<>();
}
