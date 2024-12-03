package com.saas.leave.model;

import com.saas.leave.enums.Day;
import com.saas.leave.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class LeaveRequest extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private UUID employeeId;

    @NotNull
    @Column(nullable = false)
    private int numberOfDays;

    private int numberOfApprovedDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_decision")
    private Status departmentDecision;

    @Enumerated(EnumType.STRING)
    @Column(name = "hr_decision")
    private Status hrDecision;
    @Size(max=500)

    private String comment;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate leaveStart;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Day day; // Type of leave (Full/Half Day)

    @Temporal(TemporalType.DATE)
    private LocalDate returnDate; // Return date from leave

    @Size(max = 1000)
    private String description; // Description of the leave request

    @ManyToOne
    @JoinColumn(name = "leave_type_id") // Corrected naming convention
    private LeaveType leaveType; // Type of leave

    @ManyToOne
    @JoinColumn(name = "budget_year_id") // Corrected naming convention
    private BudgetYear budgetYear; // Budget year associated with the leave

    private UUID tenantId; // Tenant ID

    // Added createdAt field
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    
}
