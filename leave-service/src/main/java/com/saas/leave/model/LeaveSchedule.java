package com.saas.leave.model;
import jakarta.persistence.*;
import com.saas.leave.enums.LeaveMonth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class LeaveSchedule extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NotNull
    private UUID employeeId;
    @Enumerated(EnumType.STRING)
    @Column(name = "leaveMonth")
    @NotNull
    private LeaveMonth leaveMonth;
    @Size(max = 1000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "budgetYear_id")
    private BudgetYear budgetYear;
    private UUID tenantId;
}
