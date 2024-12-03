package com.saas.employee.repository;

import com.saas.employee.model.EmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, UUID> {

    List<EmployeeHistory> findByTenantIdAndEmployeeId(UUID tenantId, UUID employeeId);
}
