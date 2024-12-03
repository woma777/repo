package com.saas.organization.repository;

import com.saas.organization.model.FieldOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, UUID> {
    boolean existsByFieldOfStudy(String fieldOfStudy);

    boolean existsByFieldOfStudyAndTenantId(String fieldOfStudy, UUID tenantId);
}
