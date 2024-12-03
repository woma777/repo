package com.saas.training.model;

import com.saas.training.enums.InternshipStatus;
import com.saas.training.enums.Semester;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true, exclude = "internshipPayment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipStudent extends Base{

    @NotNull
    @Column(nullable = false)
    private UUID budgetYearId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semester semester;

    private UUID placedDepartmentId;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate endDate;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String middleName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String lastName;

    @Size(max = 10)
    private String idNumber;

    @NotNull
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}")
    @Column(nullable = false)
    @Size(min = 10, max = 20)
    private String phoneNumber;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String stream;

    @NotNull
    @Column(nullable = false)
    private UUID locationId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InternshipStatus internshipStatus;

    @Size(max = 100)
    private String remark;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @OneToOne(mappedBy = "internshipStudent")
    private InternshipPayment internshipPayment;
}
