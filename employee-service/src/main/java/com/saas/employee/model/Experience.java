package com.saas.employee.model;

import com.saas.employee.enums.EmploymentType;
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
public class Experience extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String institution;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String jobTitle;

    @NotNull
    @Column(nullable = false)
    @DecimalMin(value = "0.0")
    private Double salary;

    @NotNull
    @Column(nullable = false)
    @Past
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    @PastOrPresent
    private LocalDate endDate;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String duration;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 250)
    private String responsibility;

    @Column(nullable = false)
    @Size(min = 2, max = 250)
    private String reasonForTermination;

    @NotBlank(message = "File name cannot be blank")
    @Column(nullable = false)
    @Size(min = 2, max = 20, message = "File name must be between 2 and 20 characters")
    private String fileName;

    @NotBlank(message = "File type cannot be blank")
    @Column(nullable = false)
    @Size(min = 2, max = 20, message = "File type must be between 2 and 20 characters")
    private String fileType;

    @NotBlank(message = "File cannot be blank")
    @Lob
    @Column(length = 50000000, nullable = false)
    private byte[] fileBytes;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}
