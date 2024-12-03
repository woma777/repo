package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.PreServiceCourseRequest;
import com.saas.training.dto.response.PreServiceCourseResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.repository.PreServiceCourseRepository;
import com.saas.training.repository.PreServiceTraineeRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.PreServiceCourseType;
import com.saas.training.model.PreServiceCourse;
import com.saas.training.model.PreServiceTrainee;
import com.saas.training.mapper.PreServiceCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreServiceCourseService {

    private final PreServiceCourseRepository preServiceCourseRepository;
    private final PreServiceCourseMapper preServiceCourseMapper;
    private final PreServiceTraineeRepository preServiceTraineeRepository;
    private final ValidationUtil validationUtil;

    @Transactional
    public PreServiceCourseResponse addPreServiceCourse(UUID tenantId,
                                                        PreServiceCourseRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourse preServiceCourse = preServiceCourseMapper.mapToEntity(tenant, request);
        if (preServiceCourseRepository.existsByTenantIdAndCourseName(
                tenant.getId(), request.getCourseName())) {
            throw new ResourceExistsException(
                    "Course with name '" + request.getCourseName() + "' already exists");
        }
        preServiceCourse = preServiceCourseRepository.save(preServiceCourse);
        return preServiceCourseMapper.mapToDto(preServiceCourse);
    }

    public List<PreServiceCourseResponse> getAllPreServiceCourses(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<PreServiceCourse> preServiceCourses = preServiceCourseRepository.findAll();
        return preServiceCourses.stream()
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    public PreServiceCourseResponse getPreServiceCourseById(UUID tenantId,
                                                            UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourse preServiceCourse = validationUtil
                .getPreServiceCourseById(tenant.getId(), courseId);
        return preServiceCourseMapper.mapToDto(preServiceCourse);
    }

    public List<PreServiceCourseResponse> getCoursesByTraineeId(UUID tenantId,
                                                                UUID traineeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        Set<PreServiceCourse> traineeCourses = preServiceTrainee.getPreServiceCourses();
        return traineeCourses.stream()
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    public List<PreServiceCourseResponse> getPreServiceCoursesByCourseTypeId(UUID tenantId,
                                                                             UUID courseTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), courseTypeId);
        List<PreServiceCourse> preServiceCourses = preServiceCourseRepository
                .findByTenantIdAndPreServiceCourseTypeId(tenant.getId(), preServiceCourseType.getId());
        return preServiceCourses.stream()
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    @Transactional
    public PreServiceCourseResponse updatePreServiceCourse(UUID tenantId,
                                                           UUID courseId,
                                                           PreServiceCourseRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourse preServiceCourse = validationUtil
                .getPreServiceCourseById(tenant.getId(), courseId);
        if (preServiceCourseRepository.existsByTenantIdAndCourseNameAndIdNot(
                tenant.getId(), request.getCourseName(), preServiceCourse.getId())) {
            throw new ResourceExistsException(
                    "Course with name '" + request.getCourseName() + "' already exists");
        }
        preServiceCourse = preServiceCourseMapper.updateEntity(tenant, preServiceCourse, request);
        preServiceCourse = preServiceCourseRepository.save(preServiceCourse);
        return preServiceCourseMapper.mapToDto(preServiceCourse);
    }

    @Transactional
    public void removeCourseFromTrainee(UUID tenantId,
                                        UUID traineeId,
                                        UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        PreServiceCourse courseToRemove = validationUtil.getPreServiceCourseById(tenant.getId(), courseId);
        Set<PreServiceCourse> traineeCourses = preServiceTrainee.getPreServiceCourses();
        boolean removed = traineeCourses.remove(courseToRemove);
        if (!removed) {
            throw new ResourceNotFoundException(
                    "Course not associated with the trainee '" + courseId + "'");
        }
        preServiceTrainee.setPreServiceCourses(traineeCourses);
        preServiceTraineeRepository.save(preServiceTrainee);
    }

    @Transactional
    public void deletePreServiceCourse(UUID tenantId,
                                       UUID courseId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourse preServiceCourse = validationUtil.getPreServiceCourseById(tenant.getId(), courseId);
        Set<PreServiceTrainee> preServiceTrainees = preServiceCourse.getPreServiceTrainees();
        for (PreServiceTrainee trainee : preServiceTrainees) {
            trainee.getPreServiceCourses().remove(preServiceCourse);
            preServiceTraineeRepository.save(trainee);
        }
        preServiceCourseRepository.delete(preServiceCourse);
    }
}