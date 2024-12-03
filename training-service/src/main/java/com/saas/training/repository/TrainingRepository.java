package com.saas.training.repository;

import com.saas.training.model.Training;
import com.saas.training.enums.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {

    List<Training> findByTenantIdAndTrainingStatus(UUID tenantId, TrainingStatus trainingStatus);
}
