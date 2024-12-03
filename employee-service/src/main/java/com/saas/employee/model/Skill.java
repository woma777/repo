package com.saas.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String skillType;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String skillLevel;

    @Size(max = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}
