package com.saas.leave.repository;

import com.saas.leave.model.BudgetYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BudgetYearRepository extends JpaRepository<BudgetYear, UUID> {

    // Use a custom query to find the current budget year (ensure only one active)
    @Query("SELECT b FROM BudgetYear b WHERE b.isActive = true")
    Optional<BudgetYear> findCurrentBudgetYear(UUID budgetYearId);

    boolean existsByBudgetYearAndTenantId(String budgetYear,UUID tenantId);



}