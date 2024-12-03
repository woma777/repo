package com.saas.recruitment.model;

import com.saas.recruitment.enums.Gender;
import com.saas.recruitment.enums.MaritalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true, exclude = "examResult")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String middleName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String lastName;

    @Past
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaritalStatus maritalStatus;

    @NotNull
    @Column(nullable = false)
    private UUID countryId;

    @NotNull
    @Column(nullable = false)
    private UUID locationId;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(min = 10, max = 20)
    @Column(nullable = false)
    private String phoneNumber;

    @Email
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String email;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String homeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String officeTelephone;

    @Size(max = 20)
    private String houseNumber;

    @Size(max = 20)
    private String poBox;

    @Size(max = 250)
    private String skills;

    @Size(max = 250)
    private String otherInformation;

    @Size(max = 250)
    private String hobbies;

    /*private String profileImageName;
    private String profileImageType;
    @Lob
    @Column(length = 50000000)
    private byte[] profileImageBytes;*/

    @ManyToOne
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @OneToOne(mappedBy = "applicant", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private ExamResult examResult;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Language> languages;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reference> references;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Training> trainings;
}
