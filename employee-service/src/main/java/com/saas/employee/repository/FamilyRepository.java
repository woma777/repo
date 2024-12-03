package com.saas.employee.repository;

import com.saas.employee.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {

    List<Family> findByEmployeeId(UUID empId);
}
