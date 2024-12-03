package com.saas.leave.repository;

import com.saas.leave.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, UUID> {

    /**
     * Find leave balances by employee ID.
     *
     * @param employeeId the ID of the employee
     * @return list of LeaveBalance entities for the employee
     */
    List<LeaveBalance> findByEmployeeId(UUID employeeId);

    /**
     * Find leave balances by employee ID and budget year ID.
     *
     * @param employeeId   the ID of the employee
     * @param budgetYearId the ID of the budget year
     * @return list of LeaveBalance entities for the employee and budget year
     */
    List<LeaveBalance> findByEmployeeIdAndBudgetYearId(UUID employeeId, UUID budgetYearId);

    /**
     * Find leave balances by employee ID, budget year ID, and tenant ID.
     *
     * @param employeeId   the ID of the employee
     * @param budgetYearId the ID of the budget year
     * @param tenantId     the ID of the tenant
     * @return list of LeaveBalance entities matching the criteria
     */
//    List<LeaveBalance> findByEmployeeIdAndBudgetYearId(UUID employeeId, UUID budgetYearId);
}
