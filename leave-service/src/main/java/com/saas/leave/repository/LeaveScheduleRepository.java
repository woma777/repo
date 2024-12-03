package com.saas.leave.repository;

import com.saas.leave.model.LeaveSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveScheduleRepository extends JpaRepository<LeaveSchedule, UUID> {


    boolean existsByTenantId(UUID tenantId);

    boolean existsByEmployeeId(UUID employeeId);
}
