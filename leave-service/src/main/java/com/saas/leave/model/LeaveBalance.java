package com.saas.leave.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Entity
@Data
public class LeaveBalance extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull
    @Column(name = "employee_id")
    private UUID employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_year_id")
    private BudgetYear budgetYear;

    @Column(name = "total_leave_days")
    private Integer totalLeaveDays;

    @Column(name = "remaining_leave_days")
    private int remainingDays;

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "last_updated_date") // Adjust based on your actual column name
    private LocalDate lastUpdatedDate;

    public LeaveBalance() {
        this.lastUpdatedDate = LocalDate.now(); // Initialize to the current date
    }

    // Updated constructor to initialize fields
    public LeaveBalance(UUID employeeId, Integer totalLeaveDays, int remainingDays, UUID tenantId, BudgetYear budgetYear) {
        this.employeeId = employeeId;
        this.totalLeaveDays = totalLeaveDays;
        this.remainingDays = remainingDays;
        this.tenantId = tenantId;
        this.budgetYear = budgetYear;
        this.lastUpdatedDate = LocalDate.now(); // Initialize to the current date
    }
}
