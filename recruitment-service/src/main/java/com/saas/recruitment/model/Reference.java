package com.saas.recruitment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicant_reference")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reference extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(min = 10, max = 20)
    private String phoneNumber;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String jobTitle;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String workAddress;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    @Email
    private String email;

    @Size(max = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false, updatable = false)
    private Applicant applicant;
}
