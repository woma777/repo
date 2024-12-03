package com.saas.promotion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionCriteria extends Base {

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private double weight;

    @OneToOne()
    @JoinColumn(name = "criteria_name_id", nullable = false)
    private CriteriaName criteriaName;

    @ManyToOne
    @JoinColumn(name = "parent_criteria_id")
    private PromotionCriteria parentPromotionCriteria;

    @OneToMany(mappedBy = "parentPromotionCriteria")
    private Set<PromotionCriteria> subPromotionCriteria = new HashSet<>();

    @OneToMany(mappedBy = "promotionCriteria")
    private Set<CandidateEvaluation> candidateEvaluations = new HashSet<>();
}
