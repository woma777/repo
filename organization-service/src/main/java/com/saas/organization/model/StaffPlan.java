package com.saas.organization.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffPlan extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "staff_plan_id", nullable = false, unique = true)
    private UUID id;

    @NotNull(message = "Quantity cannot be null")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "job-registration_id")
    private JobRegistration jobRegistration;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

}
