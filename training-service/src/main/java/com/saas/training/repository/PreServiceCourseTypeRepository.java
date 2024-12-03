package com.saas.training.repository;

import com.saas.training.model.PreServiceCourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PreServiceCourseTypeRepository extends JpaRepository<PreServiceCourseType, UUID> {

    boolean existsByTenantIdAndCourseType(UUID tenantId, String courseType);
    boolean existsByTenantIdAndCourseTypeAndIdNot(UUID tenantId, String courseType, UUID id);
}
