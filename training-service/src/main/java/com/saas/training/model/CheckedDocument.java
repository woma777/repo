package com.saas.training.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "preServiceTrainees")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDocument extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String documentName;

    @Size(max = 250)
    private String description;

    @ToString.Exclude
    @ManyToMany(mappedBy = "checkedDocuments", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<PreServiceTrainee> preServiceTrainees;
}
