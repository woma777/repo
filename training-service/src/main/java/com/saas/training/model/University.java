package com.saas.training.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class University extends Base {

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String universityName;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 10)
    private String abbreviatedName;

    @NotNull
    @Column(nullable = false)
    private UUID locationId;

    @NotNull
    @Column(nullable = false)
    @DecimalMin(value = "0.0")
    private Double costPerPerson;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String mobilePhoneNumber;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String telephoneNumber;

    @NotBlank
    @Email
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String email;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String fax;

    @Size(max = 30)
    private String website;

    @Size(max = 100)
    private String remark;

    @OneToMany(mappedBy = "university", fetch = FetchType.LAZY)
    private List<InternshipStudent> internshipStudents;
}
