package com.saas.leave.repository;

import com.saas.leave.model.LeaveRequest;
import com.saas.leave.enums.Status;
import feign.Param;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, UUID> {


    List<LeaveRequest> findByEmployeeId(UUID employeeId); // Returns all leave requests for an employee

    List<LeaveRequest> findByTenantIdAndDepartmentDecision(
            UUID tenantId, Status departmentDecision);
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.employeeId = :employeeId " +
            "AND ((:leaveStart BETWEEN lr.leaveStart AND lr.returnDate) " +
            "OR (:returnDate BETWEEN lr.leaveStart AND lr.returnDate) " +
            "OR (lr.leaveStart BETWEEN :leaveStart AND :returnDate))")
    List<LeaveRequest> findOverlappingLeave(
            @Param("employeeId") UUID employeeId,
            @Param("leaveStart") LocalDate leaveStart,
            @Param("returnDate") LocalDate returnDate);


    List<LeaveRequest> findByDepartmentDecision(Status departmentDecision);

    List<LeaveRequest> findByEmployeeIdAndTenantId(@NotNull UUID employeeId, UUID tenantId);
}
