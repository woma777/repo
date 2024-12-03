package com.saas.organization.model;

import com.saas.organization.enums.JobType;
import com.saas.organization.enums.ReportsTo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRegistration extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "job_reg_id", nullable = false, unique = true)
    private UUID id;

    private String jobTitle;
    private String jobCode;

    @Enumerated(EnumType.STRING)
    private ReportsTo reportsTo;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Min(value = 0, message = "Minimum experience must be greater than or equal to 0")
    @Column(name = "minimum_experience", nullable = false)
    private Integer minExperience;

    private String duties;
    private String language;
    private String skills;
    private String description;
    private String alternativeExperience;
    private String relativeExperience;

    @ManyToOne
    @JoinColumn(name = "edu_level_id")
    private EducationLevel educationLevel;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "job_cat_id")
    private JobCategory jobCategory;

    @ManyToOne
    @JoinColumn(name = "job_grade_id")
    private JobGrade jobGrade;

    @ManyToOne
    @JoinColumn(name = "work_unit_id")
    private WorkUnit workUnit;

    @ManyToOne
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToMany(mappedBy = "jobRegistration")
    private List<StaffPlan> staffPlans;


}