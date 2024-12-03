package com.saas.employee.model;

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
public class Training extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 250)
    private String trainingTitle;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 250)
    private String institution;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 250)
    private String sponsoredBy;

    @NotNull
    @Column(nullable = false)
    @PastOrPresent
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    @PastOrPresent
    private LocalDate endDate;

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
