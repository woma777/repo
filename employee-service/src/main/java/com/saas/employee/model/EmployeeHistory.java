package com.saas.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHistory extends Base{

    @NotNull(message = "Department id cannot be null")
    @Column(nullable = false)
    private UUID departmentId;

    @NotNull(message = "Job id cannot be null")
    @Column(nullable = false)
    private UUID jobId;

    @NotNull(message = "Pay grade id cannot be null")
    @Column(nullable = false)
    private UUID payGradeId;

    @NotNull(message = "Start date cannot be null")
    @Column(nullable = false)
    @Past
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @Column(nullable = false)
    private LocalDate endDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}
