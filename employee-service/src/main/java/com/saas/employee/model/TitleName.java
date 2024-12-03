package com.saas.employee.model;

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
public class TitleName extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String titleName;

    @Size(max = 250)
    private String description;

    @OneToMany(mappedBy = "titleName")
    private List<Employee> employees;
}
