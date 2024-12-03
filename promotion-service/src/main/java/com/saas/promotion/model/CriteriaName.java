package com.saas.promotion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaName extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

    @OneToOne(mappedBy = "criteriaName")
    private PromotionCriteria promotionCriteria;

    @ManyToOne
    @JoinColumn(name = "parent_criteria_id")
    private CriteriaName parentCriteriaName;

    @OneToMany(mappedBy = "parentCriteriaName")
    private Set<CriteriaName> subCriteriaName = new HashSet<>();
}
