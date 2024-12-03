package com.saas.promotion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate extends Base{

    @NotNull
    @Column(nullable = false)
    private UUID recruitmentId;

    @NotNull
    @Column(nullable = false)
    private UUID employeeId;

    @OneToMany(mappedBy = "candidate")
    private Set<CandidateEvaluation> candidateEvaluations = new HashSet<>();

    @OneToOne(mappedBy = "candidate")
    private PromoteCandidate promoteCandidate;
}
