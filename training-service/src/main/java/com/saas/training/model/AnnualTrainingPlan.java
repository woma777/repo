package com.saas.training.model;

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
public class AnnualTrainingPlan extends Base {

    @NotNull
    @Column(nullable = false)
    private UUID departmentId;
    @NotNull
    @Column(nullable = false)
    private UUID budgetYearId;
    @NotNull
    @Column(nullable = false)
    private Integer numberOfParticipants;
    @NotNull
    @Column(nullable = false)
    private Integer numberOfDays;
    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double costPerPerson;
    @NotNull
    @Column(nullable = false)
    private Integer round;
    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String venue;
    @Size(max = 100)
    private String remark;
    @ManyToOne()
    @JoinColumn(name = "course_category_id", nullable = false)
    private TrainingCourseCategory trainingCourseCategory;
    @ManyToOne()
    @JoinColumn(name = "training_course_id", nullable = false)
    private TrainingCourse trainingCourse;
    @ManyToOne()
    @JoinColumn(name = "training_institution_id", nullable = false)
    private TrainingInstitution trainingInstitution;

    @OneToMany(mappedBy = "annualTrainingPlan", fetch = FetchType.LAZY)
    private List<Training> trainings;
}
