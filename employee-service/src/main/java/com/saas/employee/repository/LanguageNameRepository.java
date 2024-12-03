package com.saas.employee.repository;

import com.saas.employee.model.LanguageName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LanguageNameRepository extends JpaRepository<LanguageName, UUID> {

    boolean existsByLanguageNameAndTenantId(String languageName, UUID tenantId);
    boolean existsByTenantIdAndLanguageNameAndIdNot(UUID tenantId, String languageName, UUID id);
}
