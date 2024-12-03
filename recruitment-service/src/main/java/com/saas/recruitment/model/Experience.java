package com.saas.recruitment.model;

import com.saas.recruitment.enums.ExperienceType;
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
@Table(name = "applicant_experience")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String institution;

    @NotBlank
    @Column(nullable = false)
    private UUID locationId;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String jobTitle;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double salary;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String responsibility;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceType experienceType;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate endDate;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String duration;

    @NotBlank
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
    @JoinColumn(name = "applicant_id", nullable = false, updatable = false)
    private Applicant applicant;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
