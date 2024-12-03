package com.saas.leave.repository;

import com.saas.leave.model.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, UUID> {

    boolean existsByLeaveTypeNameAndTenantId(String budgetYear,UUID tenantId);
}
