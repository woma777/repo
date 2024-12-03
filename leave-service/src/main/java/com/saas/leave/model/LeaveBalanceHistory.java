package com.saas.leave.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "leave_balance_history")
@Data
@NoArgsConstructor // Default constructor for Hibernate
@AllArgsConstructor
public class LeaveBalanceHistory extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "employee_id", nullable = false) // Ensure this is non-null
    private UUID employeeId;

    @Column(name = "total_leave_days", nullable = false) // Ensure this is non-null
    private Integer totalLeaveDays;

    @Column(name = "remaining_leave_days", nullable = false) // Ensure this is non-null
    private int remainingDays;

    @Column(name = "tenant_id", nullable = false) // Ensure this is non-null
    private UUID tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_year_id", nullable = false) // Ensure this relationship is non-null
    private BudgetYear budgetYear;

    @Column(name = "calculation_date", nullable = false) // Ensure this is non-null
    private LocalDate calculationDate;

    // Constructor that sets the calculation date
    public LeaveBalanceHistory(UUID employeeId, Integer totalLeaveDays, int remainingDays, UUID tenantId, BudgetYear budgetYear) {
        this.employeeId = employeeId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingDays = remainingDays;
        this.tenantId = tenantId;
        this.budgetYear = budgetYear;
        this.calculationDate = LocalDate.now(); // Initialize the calculation date
    }
}
