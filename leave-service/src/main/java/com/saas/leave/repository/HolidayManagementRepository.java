package com.saas.leave.repository;

import com.saas.leave.model.HolidayManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HolidayManagementRepository extends JpaRepository<HolidayManagement, UUID> {

    @Query("SELECT h.date FROM HolidayManagement h")
    List<LocalDate> findAllHolidays();

    boolean existsByHolidayId(UUID holidayId);

    List<HolidayManagement> findByBudgetYear_Id(UUID budgetYearId);
    // boolean existsByBudgetYearAndTenantId(String budgetYear,UUID tenantId);
}
