package com.saas.recruitment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = "recruitment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentWeight extends Base {

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
    @Min(value = 100)
    @Column(nullable = false)
    private Double total;

    @OneToOne
    @JoinColumn(name = "recruitment_id", nullable = false)
    private Recruitment recruitment;

    @OneToMany(mappedBy = "assessmentWeight")
    private List<ExamResult> examResults;
}
