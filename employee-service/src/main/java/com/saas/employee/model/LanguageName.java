package com.saas.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageName extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String languageName;

    @Size(max = 250)
    private String description;

    @OneToMany(mappedBy = "languageName")
    private Set<Language> languages;
}
