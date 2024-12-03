package com.saas.planning.model;

import com.saas.planning.enums.EmploymentType;
import com.saas.planning.enums.HowToBeFilled;
import com.saas.planning.enums.WhenToBe;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HrNeedRequest extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "no_of_position", nullable = false)
    private Integer noOfPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "how_to_be_filled", nullable = false)
    private HowToBeFilled howToBeFilled;

    @Enumerated(EnumType.STRING)
    @Column(name = "when_to_be", nullable = false)
    private WhenToBe whenToBe;

    @Column(name = "remark", length = 500)
    private String remark;

    @Column(name = "budget_year_id", nullable = false)
    private UUID budgetYearId;

    @Column(name = "department_id", nullable = false)
    private UUID departmentId;

    @Column(name = "staff_plan_id", nullable = false)
    private UUID staffPlanId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @OneToMany(mappedBy = "hrNeedRequest", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<HrAnalysis> hrAnalyses = new ArrayList<>();
    @OneToMany(mappedBy = "hrNeedRequest", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<AnnualRecruitmentAndPromotion> annualRecruitmentAndPromotions = new ArrayList<>();
}
