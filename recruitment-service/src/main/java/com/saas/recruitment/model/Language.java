package com.saas.recruitment.model;

import com.saas.recruitment.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicant_language")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language extends Base {

    @NotNull
    @Column(nullable = false)
    private UUID languageNameId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Listening listening;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Speaking speaking;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Reading reading;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Writing writing;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false, updatable = false)
    private Applicant applicant;
}
