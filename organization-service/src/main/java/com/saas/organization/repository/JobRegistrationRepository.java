package com.saas.organization.repository;

import com.saas.organization.model.Department;
import com.saas.organization.model.JobRegistration;
import com.saas.organization.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRegistrationRepository extends JpaRepository<JobRegistration, UUID> {
    Optional<JobRegistration> findByTenantId(UUID tenantId);
    List<JobRegistration> findByTenantAndDepartment(Tenant tenant, Department department);

    boolean existsByJobTitle(String jobTitle);

    boolean existsByJobCode(String jobCode);

    boolean existsByJobTitleAndTenantId(String jobTitle, UUID tenantId);
}
