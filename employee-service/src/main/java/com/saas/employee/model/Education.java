package com.saas.employee.model;

import com.saas.employee.enums.EducationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class Education extends Base {

    @NotNull
    @Column(nullable = false)
    private UUID educationLevelId;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    @NotNull
    @Column(nullable = false)
    private UUID fieldOfStudyId;

    @NotBlank
    @Column(nullable = false)
    private String institution;

    @NotBlank
    @Past
    @Column(nullable = false)
    private LocalDate startDate;

    @NotBlank
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate endDate;

    @Size(max = 250)
    private String award;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(nullable = false)
    private Double result;

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
