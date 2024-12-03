package com.saas.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyResponse extends BaseResponse{
    private String relationshipType;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private String houseNumber;
    private String homeTelephone;
    private String officeTelephone;
    private String mobileNumber;
    private String email;
    private String poBox;
    private boolean isEmergencyContact;
    private UUID employeeId;
}
