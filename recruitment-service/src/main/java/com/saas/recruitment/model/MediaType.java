package com.saas.recruitment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "advertisements")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaType extends Base{

    @NotBlank
    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String mediaTypeName;

    @Size(max = 250)
    private String description;

    @ManyToMany(mappedBy = "mediaTypes", fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements = new HashSet<>();
}
