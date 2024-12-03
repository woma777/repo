package com.saas.training.repository;

import com.saas.training.model.PreServiceTraineeResult;
import com.saas.training.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PreServiceTraineeResultRepository extends JpaRepository<PreServiceTraineeResult, UUID> {

    List<PreServiceTraineeResult> findByTenantIdAndPreServiceCourseId(UUID tenantId, UUID courseId);
    boolean existsByTenantIdAndPreServiceTraineeIdAndPreServiceCourseIdAndSemester(
            UUID tenantId, UUID preServiceTraineeId, UUID preServiceCourseId, Semester semester);
    boolean existsByTenantIdAndPreServiceTraineeIdAndPreServiceCourseIdAndSemesterAndIdNot(
            UUID tenantId, UUID preServiceTraineeId, UUID preServiceCourseId, Semester semester, UUID id);
}
