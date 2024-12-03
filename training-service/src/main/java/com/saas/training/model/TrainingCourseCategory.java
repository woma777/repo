package com.saas.training.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCourseCategory extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String categoryName;

    @Size(max = 250)
    private String description;

    @OneToMany(mappedBy = "trainingCourseCategory", fetch = FetchType.LAZY)
    private List<Training> trainings;
}
