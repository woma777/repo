package com.saas.employee.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    @NotNull(message = "Address type cannot be null")
    private String addressType;

    @NotNull(message = "Location ID cannot be null")
    private UUID locationId;

    @Size(max = 20, message = "House number must be less than 20 characters")
    private String houseNumber;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Invalid home telephone number")
    @Size(max = 20, message = "Home telephone must be less than 20 characters")
    private String homeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Invalid office telephone number")
    @Size(max = 20, message = "Office telephone must be less than 20 characters")
    private String officeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Invalid mobile number")
    @Size(max = 20, message = "Mobile number must be less than 20 characters")
    private String mobileNumber;

    @Email(message = "Invalid email address")
    @Size(max = 50, message = "Email must be less than 50 characters")
    private String email;

    @Size(max = 20, message = "PO Box must be less than 20 characters")
    private String poBox;

    @NotNull(message = "Employee ID cannot be null")
    private UUID employeeId;
}