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
public class PreServiceCourseType extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String courseType;

    @Size(max = 250)
    private String description;

    @OneToMany(mappedBy = "preServiceCourseType", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PreServiceCourse> preServiceCourses;
}
