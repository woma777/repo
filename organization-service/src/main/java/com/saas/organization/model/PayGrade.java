package com.saas.organization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayGrade extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pay_grade_id", nullable = false, unique = true)
    private UUID id;

    @NotNull(message = "Initial salary cannot be null")
    @Column(name = "initial_salary", nullable = false)
    private Double initialSalary;

    @NotNull(message = "Maximum salary cannot be null")
    @Column(name = "maximum_salary", nullable = false)
    private Double maximumSalary;

    @NotNull(message = "Salary step cannot be null")
    @Column(name = "salary_step", nullable = false)
    private Integer salaryStep;

    private Double salary;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "jobGrade_id")
    private JobGrade jobGrade;
}

