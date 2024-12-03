package com.saas.organization.repository;

import com.saas.organization.model.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, UUID> {
    boolean existsByLocationTypeNameAndTenantId(String locationTypeName, UUID tenantId);

    boolean existsByLocationTypeNameAndTenantIdAndIdNot(String locationTypeName, UUID tenantId, UUID id);
}
