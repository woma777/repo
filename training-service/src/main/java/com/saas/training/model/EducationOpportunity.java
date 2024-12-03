package com.saas.training.model;

import com.saas.training.enums.TrainingLocation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationOpportunity extends Base{

    @NotNull
    @Column(nullable = false)
    private UUID budgetYearId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingLocation trainingLocation;

    @NotNull
    @Column(nullable = false)
    private UUID countryId;

    @NotNull
    @Column(nullable = false)
    private UUID educationLevelId;

    @NotNull
    @Column(nullable = false)
    private UUID qualificationId;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String sponsor;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String institution;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate endDate;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate letterDate;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String letterReferenceNumber;

    @Size(max = 100)
    private String remark;

    @NotNull
    @Column(nullable = false)
    private UUID employeeId;

    @NotNull
    @Column(nullable = false)
    private Double totalResult;
}
