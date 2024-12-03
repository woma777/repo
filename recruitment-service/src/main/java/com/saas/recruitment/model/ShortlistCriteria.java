package com.saas.recruitment.model;

import com.saas.recruitment.enums.ExperienceType;
import com.saas.recruitment.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortlistCriteria extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String criteriaName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull
    @Column(nullable = false)
    private UUID educationLevelId;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 250)
    private String trainingOrCertificate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceType experienceType;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double CGPA;

    @NotNull
    @Column(nullable = false)
    private Integer minimumExperience;

    @NotNull
    @Column(nullable = false)
    private Integer minimumAge;

    @NotNull
    @Column(nullable = false)
    private Integer maximumAge;

    @Size(max = 100)
    private String other;

    @ManyToOne
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;
}
