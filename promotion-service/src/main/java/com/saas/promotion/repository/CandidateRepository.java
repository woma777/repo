package com.saas.promotion.repository;

import com.saas.promotion.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    boolean existsByTenantIdAndRecruitmentIdAndEmployeeId(
            UUID tenantId, UUID recruitmentId, UUID employeeId);
    boolean existsByTenantIdAndRecruitmentIdAndEmployeeIdAndIdNot(
            UUID tenantId, UUID recruitmentId, UUID employeeId, UUID id);

    List<Candidate> findByTenantId(UUID tenantId);
}
