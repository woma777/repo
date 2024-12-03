package com.saas.training.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipStudentRequest {

    @NotNull(message = "Budget year ID cannot be null")
    private UUID budgetYearId;

    @NotNull(message = "Semester cannot be null")
    private String semester;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be a present or future date")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be a present or future date")
    private LocalDate endDate;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    @Size(min = 2, max = 20, message = "Middle name must be between 2 and 20 characters")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @Size(min = 2, max = 20, message = "ID number must be between 2 and 20 characters")
    private String idNumber;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Phone number must be a valid phone number")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Stream cannot be blank")
    @Size(min = 2, max = 30, message = "Stream must be between 2 and 30 characters")
    private String stream;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    @Size(max = 100, message = "Remark must be less than 100 characters")
    private String remark;

    @NotNull(message = "University ID cannot be null")
    private UUID universityId;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
