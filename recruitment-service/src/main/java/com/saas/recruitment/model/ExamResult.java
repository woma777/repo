package com.saas.recruitment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true, exclude = "applicant")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResult extends Base {

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private Double writtenExam;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private Double interview;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private Double CGPA;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private Double experience;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private Double practicalExam;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private Double other;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private Double total;

    @OneToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn(name = "assessment_weight_id", nullable = false)
    private AssessmentWeight assessmentWeight;
}
