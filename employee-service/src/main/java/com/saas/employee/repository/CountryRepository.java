package com.saas.employee.repository;

import com.saas.employee.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {

    boolean existsByTenantIdAndName(UUID tenantId, String name);
    boolean existsByTenantIdAndNameAndIdNot(UUID tenantId, String name, UUID id);

    List<Country> findByTenantId(UUID tenantId);

    Optional<Country> findByTenantIdAndId(UUID tenantId, UUID id);
}
