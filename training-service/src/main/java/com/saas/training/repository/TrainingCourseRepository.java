package com.saas.training.repository;

import com.saas.training.model.TrainingCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingCourseRepository extends JpaRepository<TrainingCourse, UUID> {

    boolean existsByTenantIdAndCourseName(UUID tenantId, String courseName);
    boolean existsByTenantIdAndCourseNameAndIdNot(UUID tenantId, String courseName, UUID id);
}
