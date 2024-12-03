package com.saas.recruitment.model;

import com.saas.recruitment.enums.RecruitmentMode;
import com.saas.recruitment.enums.RecruitmentStatus;
import com.saas.recruitment.enums.RecruitmentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true,
        exclude = { "advertisement", "assessmentWeight"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment extends Base {

    @NotNull
    @Column(nullable = false)
    private UUID requesterEmployeeId;

    @NotNull
    @Column(nullable = false)
    private UUID departmentId;

    @NotNull
    @Column(nullable = false)
    private UUID jobId;

    @Min (value = 1)
    @Column(nullable = false)
    private Integer numberOfEmployeesRequested;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentType recruitmentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentMode recruitmentMode;

    @Min (value = 0)
    private Integer numberOfEmployeesApproved;

    @Size(max = 20)
    private String vacancyNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus recruitmentStatus;

    @Size(max = 250)
    private String remark;

    @OneToOne(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Advertisement advertisement;

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Applicant> applicants;

    @OneToOne(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AssessmentWeight assessmentWeight;

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamResult> examResult;

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ShortlistCriteria> shortlistCriteria;
}
