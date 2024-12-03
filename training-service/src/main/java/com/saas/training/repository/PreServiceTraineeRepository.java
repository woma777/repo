package com.saas.training.repository;

import com.saas.training.model.PreServiceTrainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PreServiceTraineeRepository extends JpaRepository<PreServiceTrainee, UUID> {

    List<PreServiceTrainee> findByTenantIdAndBudgetYearId(UUID tenantId, UUID yearId);
    boolean existsByTenantIdAndTraineeId(UUID tenantId, String traineeId);
    boolean existsByTenantIdAndTraineeIdAndIdNot(UUID tenantId, String traineeId, UUID id);
}
