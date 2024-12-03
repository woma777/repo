package com.saas.organization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class Address extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id", nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Block number cannot be blank")
    @Column(name = "block_number", nullable = false)
    private String blockNo;

    @NotBlank(message = "Floor cannot be blank")
    @Column(name = "floor", nullable = false)
    private String floor;

    @NotBlank(message = "Office number cannot be blank")
    @Column(name = "office_number", nullable = false)
    private String officeNumber;

    @NotBlank(message = "Office telephone cannot be blank")
    @Column(name = "office_telephone", nullable = false)
    private String officeTelephone;

    @NotBlank(message = "Mobile number cannot be blank")
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "po_box")
    private String poBox;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;


}

