package com.saas.recruitment.repository;

import com.saas.recruitment.model.Recruitment;
import com.saas.recruitment.model.ShortlistCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShortlistCriteriaRepository extends JpaRepository<ShortlistCriteria, UUID> {

    List<ShortlistCriteria> findByTenantIdAndRecruitment(UUID tenantId, Recruitment recruitment);
}
