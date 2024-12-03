package com.saas.employee.model;

import com.saas.employee.enums.Gender;
import com.saas.employee.enums.RelationshipType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Family extends Base {

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 15)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 15)
    private String middleName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 15)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Past
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Size(max = 20)
    private String houseNumber;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String homeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String officeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String mobileNumber;

    @Email
    @Size(max = 50)
    private String email;

    @Size(max = 20)
    private String poBox;

    @NotNull
    @Column(nullable = false)
    private boolean isEmergencyContact;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}
