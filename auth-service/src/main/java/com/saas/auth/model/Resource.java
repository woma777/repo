package com.saas.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Resource extends Base{

    @NotBlank(message = "Resource name cannot be blank")
    private String resourceName;

    @NotBlank(message = "Service name cannot be blank")
    private String serviceName;

    private Set<String> requiredRoles = new HashSet<>();

    private String description;
}
