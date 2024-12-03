package com.saas.training.repository;

import com.saas.training.model.InternshipStudent;
import com.saas.training.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InternshipStudentRepository extends JpaRepository<InternshipStudent, UUID> {

    List<InternshipStudent> findByTenantIdAndBudgetYearIdAndSemester(
            UUID tenantId, UUID budgetYearId, Semester semester);
    boolean existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumber(
            UUID tenantId, UUID yearId, Semester semester, String idNumber);
    boolean existsByTenantIdAndBudgetYearIdAndSemesterAndIdNumberAndIdNot(
            UUID tenantId, UUID yearId, Semester semester, String idNumber, UUID id);
}
