package org.saas.transfer.repository;

import org.saas.transfer.model.DirectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DirectAssignmentRepository extends JpaRepository<DirectAssignment, UUID> {

    DirectAssignment findByTenantIdAndId(UUID tenantId, UUID directAssignmentId);


    // Method to find all DirectAssignments by tenantId
    List<DirectAssignment> findAllByTenantId(UUID tenantId);

//    Optional<DirectAssignment> findByTenantIdAndDirectAssignmentId(UUID tenantId, UUID directAssignmentId);
}
