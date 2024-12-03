package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.PreServiceTraineeResultRequest;
import com.saas.training.dto.response.PreServiceTraineeResultResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.repository.PreServiceCourseRepository;
import com.saas.training.repository.PreServiceTraineeResultRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.PreServiceCourse;
import com.saas.training.model.PreServiceTrainee;
import com.saas.training.model.PreServiceTraineeResult;
import com.saas.training.enums.Semester;
import com.saas.training.mapper.PreServiceTraineeResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreServiceTraineeResultService {

    private final PreServiceTraineeResultRepository traineeResultRepository;
    private final PreServiceCourseRepository courseRepository;
    private final PreServiceTraineeResultMapper traineeResultMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public PreServiceTraineeResultResponse addTraineeResult(UUID tenantId,
                                                            UUID traineeId,
                                                            UUID courseId,
                                                            PreServiceTraineeResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee trainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        List<PreServiceCourse> traineeCourses = courseRepository
                .findByTenantIdAndPreServiceTraineesId(tenant.getId(), traineeId)
                .stream()
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .toList();
        PreServiceCourse course = traineeCourses.stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId + "' for trainee with id '" + traineeId + "'"));
        if (traineeResultRepository.existsByTenantIdAndPreServiceTraineeIdAndPreServiceCourseIdAndSemester(
                tenant.getId(), trainee.getId(), course.getId(),
                Semester.valueOf(request.getSemester().toUpperCase()))) {
            throw new ResourceExistsException("The result for trainee with id '" + trainee.getId() +
                    "' and course with id '" + course.getId() +
                    "' in the '" + request.getSemester() + "' semester already exists.");
        }
        PreServiceTraineeResult traineeResult = traineeResultMapper.mapToEntity(tenant, trainee, course, request);
        traineeResult = traineeResultRepository.save(traineeResult);
        return traineeResultMapper.mapToDto(traineeResult);
    }

    public List<PreServiceTraineeResultResponse> getTraineesResultByCourseId(UUID tenantId,
                                                                             UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourse course = validationUtil.getPreServiceCourseById(tenant.getId(), courseId);
        List<PreServiceTraineeResult> traineeResults = traineeResultRepository
                .findByTenantIdAndPreServiceCourseId(tenant.getId(), course.getId());
        return traineeResults.stream()
                .filter(tr -> tr.getTenantId().equals(tenant.getId()))
                .map(traineeResultMapper::mapToDto)
                .toList();
    }

    public PreServiceTraineeResultResponse getTraineeCourseResultById(UUID tenantId,
                                                                      UUID traineeId,
                                                                      UUID courseId,
                                                                      UUID resultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee trainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        PreServiceCourse course = validationUtil.getPreServiceCourseById(tenant.getId(), courseId);
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        return traineeResultMapper.mapToDto(result);
    }

    public PreServiceTraineeResultResponse getTraineeResultById(UUID tenantId,
                                                                UUID resultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        return traineeResultMapper.mapToDto(result);
    }

    @Transactional
    public PreServiceTraineeResultResponse updateTraineeResult(UUID tenantId,
                                                               UUID traineeId,
                                                               UUID courseId,
                                                               UUID resultId,
                                                               PreServiceTraineeResultRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee trainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        List<PreServiceCourse> traineeCourses = courseRepository
                .findByTenantIdAndPreServiceTraineesId(tenant.getId(), traineeId)
                .stream()
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .toList();
        PreServiceCourse course = traineeCourses.stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id '" + courseId +
                                "' for trainee with id '" + traineeId + "'"));
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        if (traineeResultRepository
                .existsByTenantIdAndPreServiceTraineeIdAndPreServiceCourseIdAndSemesterAndIdNot(
                        tenant.getId(), trainee.getId(), course.getId(),
                        Semester.valueOf(request.getSemester().toUpperCase()), result.getId())) {
            throw new ResourceExistsException("The result for trainee with id '" + trainee.getId() +
                    "' and course with id '" + course.getId() +
                    "' in the '" + request.getSemester() + "' semester already exists.");
        }
        PreServiceTraineeResult traineeResult = traineeResultMapper.updateEntity(result, request);
        traineeResult = traineeResultRepository.save(traineeResult);
        return traineeResultMapper.mapToDto(traineeResult);
    }

    @Transactional
    public void deleteTraineeResult(UUID tenantId,
                                    UUID resultId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTraineeResult result = getResultById(tenant.getId(), resultId);
        traineeResultRepository.delete(result);
    }

    public PreServiceTraineeResult getResultById(UUID tenantId, UUID resultId) {

        return traineeResultRepository.findById(resultId)
                .filter(c -> c.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Result not found with id '" + resultId + "'"));
    }
}