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
public class EmployeeResponse extends BaseResponse{
    private String employeeId;
    private UUID titleNameId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private String employmentType;
    private UUID departmentId;
    private UUID jobId;
    private UUID payGradeId;
    private UUID dutyStationId;
    private UUID countryId;
    private String fydaNumber;
    private String passportNumber;
    private String tinNumber;
    private String pensionNumber;
    private String email;
    private LocalDate hiredDate;
    private LocalDate endDate;
    private String profileImageName;
    private String profileImageType;
    private byte[] profileImageBytes;
}


