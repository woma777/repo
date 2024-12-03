package com.saas.leave.repository;

import com.saas.leave.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HolidayRepository extends JpaRepository<Holiday, UUID> {
    boolean existsByHolidayName(String holidayName);
    boolean existsByHolidayNameAndTenantId(String holidayName,UUID tenantId);


}
