package com.saas.employee.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "Employee ID cannot be blank")
    @Size(min = 2, max = 10, message = "Employee ID must be between 2 and 10 characters")
    private String employeeId;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 15, message = "First name must be between 2 and 15 characters")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    @Size(min = 2, max = 15, message = "Middle name must be between 2 and 15 characters")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 15, message = "Last name must be between 2 and 15 characters")
    private String lastName;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Marital status cannot be null")
    private String maritalStatus;

    @NotNull(message = "Employment type cannot be null")
    private String employmentType;

    @NotNull(message = "Department ID cannot be null")
    private UUID departmentId;

    @NotNull(message = "Job ID cannot be null")
    private UUID jobId;

    @NotNull(message = "Pay grade ID cannot be null")
    private UUID payGradeId;

    @NotNull(message = "Hired date cannot be null")
    @PastOrPresent(message = "Hired date must be in the past or present")
    private LocalDate hiredDate;

    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDate endDate;

    @Size(min = 16, max = 16, message = "Fyda number must be 16 characters")
    private String fydaNumber;

    @Size(max = 15, message = "Passport number must be less than 15 characters")
    private String passportNumber;

    @Size(max = 15, message = "TIN number must be less than 15 characters")
    private String tinNumber;

    @Size(max = 15, message = "Pension number must be less than 15 characters")
    private String pensionNumber;

    @NotBlank(message = "Email cannot be blank")
    @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotNull(message = "Title name ID cannot be null")
    private UUID titleNameId;

    @NotNull(message = "Duty station ID cannot be null")
    private UUID dutyStationId;

    @NotNull(message = "Country ID cannot be null")
    private UUID countryId;

    @AssertTrue(message = "End date must be after hired date")
    public boolean isEndDateValid() {
        if (hiredDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(hiredDate);
    }
}


