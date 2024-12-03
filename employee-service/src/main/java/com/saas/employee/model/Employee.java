package com.saas.employee.model;

import com.saas.employee.enums.EmploymentType;
import com.saas.employee.enums.Gender;
import com.saas.employee.enums.MaritalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends Base {

    @NotBlank
    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 10)
    private String employeeId;

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

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @NotNull
    @Column(nullable = false)
    private UUID departmentId;

    @NotNull
    @Column(nullable = false)
    private UUID jobId;

    @NotNull
    @Column(nullable = false)
    private UUID payGradeId;

    @NotNull
    @Column(nullable = false)
    @PastOrPresent
    private LocalDate hiredDate;

    @FutureOrPresent
    private LocalDate endDate;

    @Size(min = 16, max = 16)
    private String fydaNumber;

    @Size(max = 15)
    private String passportNumber;

    @Size(max = 15)
    private String tinNumber;

    @Size(max = 15)
    private String pensionNumber;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    @Email
    private String email;

    private String profileImageName;
    private String profileImageType;
    @Lob
    @Column(length = 50000000)
    private byte[] profileImageBytes;

    @ManyToOne
    @JoinColumn(name = "title_name_id", nullable = false)
    private TitleName titleName;

    @ManyToOne
    @JoinColumn(name = "duty_station_id", nullable = false)
    private DutyStation dutyStation;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "employee")
    private Set<Address> addresses;

    @OneToMany(mappedBy = "employee")
    private Set<Education> educations;

    @OneToMany(mappedBy = "employee")
    private Set<Experience> experiences;

    @OneToMany(mappedBy = "employee")
    private Set<Family> families;

    @OneToMany(mappedBy = "employee")
    private Set<Language> languages;

    @OneToMany(mappedBy = "employee")
    private Set<Reference> references;

    @OneToMany(mappedBy = "employee")
    private Set<Skill> skills;

    @OneToMany(mappedBy = "employee")
    private Set<Training> trainings = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<EmployeeHistory> employeeHistories = new HashSet<>();
}
