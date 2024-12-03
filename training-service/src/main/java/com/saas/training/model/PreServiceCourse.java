package com.saas.training.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "preServiceTrainees")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceCourse extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String courseName;

    @Size(max = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_type_id", nullable = false)
    private PreServiceCourseType preServiceCourseType;

    @ManyToMany(mappedBy = "preServiceCourses", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<PreServiceTrainee> preServiceTrainees;

    @OneToMany(mappedBy = "preServiceCourse", fetch = FetchType.LAZY)
    private List<PreServiceTraineeResult> preServiceTraineeResults;
}
