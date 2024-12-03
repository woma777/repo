package com.saas.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DutyStation extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

    @OneToMany(mappedBy = "dutyStation")
    private Set<Employee> employees;
}
