package com.saas.planning.repository;

import com.saas.planning.model.HrAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HrAnalysisRepository extends JpaRepository<HrAnalysis, UUID> {
    // Add any custom query methods if necessary
}
