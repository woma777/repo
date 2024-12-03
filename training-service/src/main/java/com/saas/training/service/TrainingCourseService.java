package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.TrainingCourseRequest;
import com.saas.training.dto.response.TrainingCourseResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.repository.TrainingCourseRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.TrainingCourse;
import com.saas.training.mapper.TrainingCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingCourseService {

    private final TrainingCourseRepository trainingCourseRepository;
    private final TrainingCourseMapper trainingCourseMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingCourseResponse addTrainingCourse(UUID tenantId,
                                                    TrainingCourseRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (trainingCourseRepository.existsByTenantIdAndCourseName(
                tenant.getId(), request.getCourseName())) {
            throw new ResourceExistsException(
                    "Training course with Name '" + request.getCourseName() + "' already exists");
        }
        TrainingCourse trainingCourse = trainingCourseMapper.mapToEntity(tenant, request);
        trainingCourse = trainingCourseRepository.save(trainingCourse);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    public List<TrainingCourseResponse> getAllTrainingCourses(UUID tenantId,
                                                              UUID courseCategoryId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<TrainingCourse> trainingCourses = trainingCourseRepository.findAll();
        return trainingCourses.stream()
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .filter(course -> course.getTrainingCourseCategory().getId().equals(courseCategoryId))
                .map(trainingCourseMapper::mapToDto)
                .toList();
    }

    public TrainingCourseResponse getTrainingCourseById(UUID tenantId,
                                                        UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourse trainingCourse = validationUtil.getTrainingCourseById(tenant.getId(), courseId);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    @Transactional
    public TrainingCourseResponse updateTrainingCourse(UUID tenantId,
                                                       UUID courseId,
                                                       TrainingCourseRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourse trainingCourse = validationUtil.getTrainingCourseById(tenant.getId(), courseId);
        if (trainingCourseRepository.existsByTenantIdAndCourseNameAndIdNot(
                tenant.getId(), request.getCourseName(), trainingCourse.getId())) {
            throw new ResourceExistsException(
                    "Training course with Name '" + request.getCourseName() + "' already exists");
        }
        trainingCourse = trainingCourseMapper.updateEntity(tenant, trainingCourse, request);
        trainingCourse = trainingCourseRepository.save(trainingCourse);
        return trainingCourseMapper.mapToDto(trainingCourse);
    }

    @Transactional
    public void deleteTrainingCourse(UUID tenantId,
                                     UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourse trainingCourse = validationUtil.getTrainingCourseById(tenant.getId(), courseId);
        trainingCourseRepository.delete(trainingCourse);
    }
}