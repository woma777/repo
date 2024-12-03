package com.saas.leave.repository;

import com.saas.leave.model.LeaveBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveBalanceHistoryRepository extends JpaRepository<LeaveBalanceHistory, UUID> {
    List<LeaveBalanceHistory> findByEmployeeId(UUID employeeId);
}