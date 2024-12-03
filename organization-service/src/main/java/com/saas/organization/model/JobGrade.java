package com.saas.organization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JobGrade extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "job_grade_name  cannot be blank")
    @Column(name = "job_grade_name", nullable = false)
    private String jobGradeName;

    @NotBlank(message = "description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToMany(mappedBy = "jobGrade", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<JobRegistration> jobRegistrations;

    @OneToMany(mappedBy = "jobGrade", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PayGrade> payGrades;
//    @OneToMany(mappedBy = "jobGrade", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private List<StaffPlan> staffPlans;



}
