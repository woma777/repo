package com.saas.training.model;

import com.saas.training.enums.TrainingStatus;
import com.saas.training.enums.TrainingLocation;
import com.saas.training.enums.TrainingType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training extends Base {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingType trainingType;

    @NotNull
    @Column(nullable = false)
    private UUID departmentId;

    @NotNull
    @Column(nullable = false)
    private UUID budgetYearId;

    @NotNull
    @Column(nullable = false)
    @Min(value = 1)
    private Integer numberOfParticipants;

    @NotNull
    @Column(nullable = false)
    @Min(value = 1)
    private Integer numberOfDays;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String venue;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double costPerPerson;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String sponsoredBy;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingLocation trainingLocation;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String reason;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingStatus trainingStatus;

    @Size(max = 100)
    private String remark;

    @ManyToOne
    @JoinColumn(name = "course_category_id", nullable = false)
    private TrainingCourseCategory trainingCourseCategory;

    @ManyToOne
    @JoinColumn(name = "training_course_id", nullable = false)
    private TrainingCourse trainingCourse;

    @ManyToOne
    @JoinColumn(name = "training_institution_id", nullable = false)
    private TrainingInstitution trainingInstitution;

    @ManyToOne
    @JoinColumn(name = "training_plan_id")
    private AnnualTrainingPlan annualTrainingPlan;

    @OneToMany(mappedBy = "training", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TrainingParticipant> trainingParticipants;
}