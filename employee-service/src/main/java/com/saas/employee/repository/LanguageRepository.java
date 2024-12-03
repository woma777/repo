package com.saas.employee.repository;

import com.saas.employee.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {

    List<Language> findByEmployeeId(UUID empId);
}
