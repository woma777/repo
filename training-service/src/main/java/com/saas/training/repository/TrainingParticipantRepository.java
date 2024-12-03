package com.saas.training.repository;

import com.saas.training.model.TrainingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingParticipantRepository extends JpaRepository<TrainingParticipant, UUID> {

    boolean existsByTenantIdAndTrainingIdAndParticipantEmployeeId(
            UUID tenantId, UUID trainingId, UUID employeeId);
    boolean existsByTenantIdAndTrainingIdAndParticipantEmployeeIdAndIdNot(
            UUID tenantId, UUID trainingId, UUID employeeId, UUID id);
}
