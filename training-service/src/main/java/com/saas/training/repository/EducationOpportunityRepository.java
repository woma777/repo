package com.saas.training.repository;

import com.saas.training.model.EducationOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EducationOpportunityRepository extends JpaRepository<EducationOpportunity, UUID> {

    List<EducationOpportunity> findByTenantIdAndEmployeeId(UUID tenantId, UUID employeeId);
    boolean existsByTenantIdAndBudgetYearIdAndEmployeeId(UUID tenantId, UUID budgetYearId, UUID employeeId);
    boolean existsByTenantIdAndBudgetYearIdAndEmployeeIdAndIdNot(
            UUID tenantId, UUID budgetYearId, UUID employeeId, UUID id);
}
