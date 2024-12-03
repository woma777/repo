package com.saas.recruitment.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceRequest {

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$", message = "Phone number must be a valid phone number")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Job title cannot be blank")
    @Size(min = 2, max = 50, message = "Job title must be between 2 and 50 characters")
    private String jobTitle;

    @NotBlank(message = "Work address cannot be blank")
    @Size(min = 2, max = 50, message = "Work address must be between 2 and 50 characters")
    private String workAddress;

    @NotBlank(message = "Email cannot be blank")
    @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;
}
