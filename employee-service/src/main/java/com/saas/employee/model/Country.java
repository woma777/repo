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
public class Country extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 10)
    private String abbreviatedName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 10)
    private String code;

    @OneToMany(mappedBy = "country")
    private Set<Employee> employees;
}
