package com.saas.employee.model;

import com.saas.employee.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language extends Base {

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Listening listening;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Speaking speaking;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Reading reading;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Writing writing;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "language_name_id", nullable = false)
    private LanguageName languageName;}
