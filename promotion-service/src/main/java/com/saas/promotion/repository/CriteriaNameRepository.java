package com.saas.promotion.repository;

import com.saas.promotion.model.CriteriaName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CriteriaNameRepository extends JpaRepository<CriteriaName, UUID> {

    List<CriteriaName> findByTenantId(UUID tenantId);

    List<CriteriaName> findByTenantIdAndParentCriteriaName(
            UUID tenantId, CriteriaName criteriaName);

    boolean existsByTenantIdAndName(UUID tenantId, String criteriaName);

    boolean existsByTenantIdAndNameAndIdNot(UUID tenantId, String criteriaName, UUID id);

    boolean existsByTenantIdAndParentCriteriaNameAndName(
            UUID tenantId, CriteriaName parentCriteriaName, String criteriaName);
}
