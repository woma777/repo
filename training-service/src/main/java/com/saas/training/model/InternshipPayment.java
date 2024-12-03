package com.saas.training.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true, exclude = "internshipStudent")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipPayment extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 20)
    private String referenceLetter;

    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate endDate;

    @NotNull
    @DecimalMin(value = "0.0")
    private Double paymentAmount;

    @Size(max = 100)
    private String remark;

    @OneToOne
    @JoinColumn(name = "intern_id", nullable = false)
    private InternshipStudent internshipStudent;
}
