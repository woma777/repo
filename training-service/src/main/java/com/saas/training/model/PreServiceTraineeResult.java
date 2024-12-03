package com.saas.training.model;

import com.saas.training.enums.Decision;
import com.saas.training.enums.Semester;
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
public class PreServiceTraineeResult extends Base{

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semester semester;

    @NotNull
    @Column(nullable = false)
    @DecimalMin(value = "0.0")
    private Double result;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Decision decision;

    @Size(max = 100)
    private String remark;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private PreServiceTrainee preServiceTrainee;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private PreServiceCourse preServiceCourse;
}
