package com.saas.employee.repository;

import com.saas.employee.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<Training, UUID> {

    List<Training> findByEmployeeId(UUID empId);
}
