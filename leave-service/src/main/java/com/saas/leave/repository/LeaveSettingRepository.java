package com.saas.leave.repository;

import com.saas.leave.model.LeaveSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeaveSettingRepository extends JpaRepository<LeaveSetting, UUID> {
  List<LeaveSetting> findByTenantId(UUID tenantId);
//    List<LeaveSetting> findByTenantId(UUID tenantId);


    boolean existsByTenantId(UUID id);

    boolean existsByLeaveTypeId(UUID leaveTypeId);
}
