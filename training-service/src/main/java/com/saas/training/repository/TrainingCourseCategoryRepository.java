package com.saas.training.repository;

import com.saas.training.model.TrainingCourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingCourseCategoryRepository extends JpaRepository<TrainingCourseCategory, UUID> {

    boolean existsByTenantIdAndCategoryName(UUID tenantId, String categoryName);
    boolean existsByTenantIdAndCategoryNameAndIdNot(UUID tenantId, String categoryName, UUID id);
}
