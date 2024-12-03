package com.saas.recruitment.model;

import com.saas.recruitment.enums.EducationType;
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
@Table(name = "applicant_education")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Education extends Base {

    @NotNull
    @Column(nullable = false)
    private UUID educationLevelId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationType educationType;

    @NotNull
    @Column(nullable = false)
    private UUID fieldOfStudyId;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String institution;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate endDate;

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
    @JoinColumn(name = "applicant_id", nullable = false, updatable = false)
    private Applicant applicant;
}
