package com.saas.employee.model;

import com.saas.employee.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address extends Base {

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @NotNull
    @Column(nullable = false)
    private UUID locationId;

    @Size(max = 20)
    private String houseNumber;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String homeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String officeTelephone;

    @Pattern(regexp = "^\\+?[0-9 .\\-()]{7,15}$")
    @Size(max = 20)
    private String mobileNumber;

    @Email
    @Size(max = 50)
    private String email;

    @Size(max = 20)
    private String poBox;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}