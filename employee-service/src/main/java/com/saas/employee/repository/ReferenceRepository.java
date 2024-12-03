package com.saas.employee.repository;

import com.saas.employee.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, UUID> {

    List<Reference> findByEmployeeId(UUID empId);
}
