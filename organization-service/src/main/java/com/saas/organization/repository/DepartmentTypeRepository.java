package com.saas.organization.repository;

import com.saas.organization.model.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentTypeRepository extends JpaRepository<DepartmentType, UUID> {
    Optional<DepartmentType> findByTenantId(UUID tenantId);

    boolean existsByDepartmentTypeName(String departmentTypeName);

    boolean existsByDepartmentTypeNameAndTenantId(String departmentTypeName, UUID tenantId);
}
