package com.saas.training.repository;

import com.saas.training.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UniversityRepository extends JpaRepository<University, UUID> {

    boolean existsByTenantIdAndUniversityName(UUID tenantId, String universityName);
    boolean existsByTenantIdAndUniversityNameAndIdNot(UUID tenantId, String universityName, UUID id);
}
