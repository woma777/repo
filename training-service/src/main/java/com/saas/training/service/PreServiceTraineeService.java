package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.PreServiceTraineeRequest;
import com.saas.training.dto.response.PreServiceCourseResponse;
import com.saas.training.dto.response.PreServiceTraineeResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.exception.ResourceNotFoundException;
import com.saas.training.repository.PreServiceTraineeRepository;
import com.saas.training.utility.FileUtil;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.CheckedDocument;
import com.saas.training.model.PreServiceCourse;
import com.saas.training.model.PreServiceTrainee;
import com.saas.training.mapper.PreServiceCourseMapper;
import com.saas.training.mapper.PreServiceTraineeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreServiceTraineeService {

    private final PreServiceTraineeRepository preServiceTraineeRepository;
    private final PreServiceTraineeMapper preServiceTraineeMapper;
    private final PreServiceCourseMapper preServiceCourseMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public PreServiceTraineeResponse createPreServiceTrainee(UUID tenantId,
                                                             PreServiceTraineeRequest request,
                                                             MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (preServiceTraineeRepository.existsByTenantIdAndTraineeId(
                tenant.getId(), request.getTraineeId())) {
            throw new ResourceExistsException(
                    "Trainee with trainee Id '" + request.getTraineeId() + "' already exists");
        }
        PreServiceTrainee preServiceTrainee = preServiceTraineeMapper.mapToEntity(tenant, request, file);
        preServiceTrainee = preServiceTraineeRepository.save(preServiceTrainee);
        return preServiceTraineeMapper.mapToDto(preServiceTrainee);
    }

    public List<PreServiceTraineeResponse> getAllPreServiceTrainees(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<PreServiceTrainee> preServiceTrainees = preServiceTraineeRepository.findAll();
        return preServiceTrainees.stream()
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .map(preServiceTraineeMapper::mapToDto)
                .toList();
    }

    public PreServiceTraineeResponse getPreServiceTraineeById(UUID tenantId,
                                                              UUID traineeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        return preServiceTraineeMapper.mapToDto(preServiceTrainee);
    }

    public List<PreServiceTraineeResponse> getPreServiceTraineesByYear(UUID tenantId,
                                                                       UUID yearId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<PreServiceTrainee> preServiceTrainees = preServiceTraineeRepository
                .findByTenantIdAndBudgetYearId(tenant.getId(), yearId);
        return preServiceTrainees.stream()
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .map(preServiceTraineeMapper::mapToDto)
                .toList();
    }

    public byte[] getTraineeProfileImageById(UUID tenantId,
                                             UUID traineeId,
                                             MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee trainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        if (trainee.getImageType() == null) {
            throw new ResourceNotFoundException("Trainee image type is not specified");
        }
        switch (trainee.getImageType()) {
            case "image/jpeg":
                mediaType = MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf(MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf(MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + trainee.getImageType());
        }
        byte[] imageBytes = FileUtil.decompressFile(trainee.getImage());
        if (imageBytes.length == 0) {
            throw new ResourceNotFoundException("Image data is not available");
        }
        return imageBytes;
    }

    @Transactional
    public List<PreServiceCourseResponse> addCoursesToTrainee(UUID tenantId,
                                                              UUID traineeId,
                                                              Set<UUID> courseIds) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        if (courseIds != null && !courseIds.isEmpty()) {
            Set<PreServiceCourse> preServiceCourses = new HashSet<>();
            for (UUID courseId : courseIds) {
                PreServiceCourse course = validationUtil.getPreServiceCourseById(tenant.getId(), courseId);
                preServiceCourses.add(course);
            }
            preServiceTrainee.setPreServiceCourses(preServiceCourses);
        }
        preServiceTrainee = preServiceTraineeRepository.save(preServiceTrainee);
        Set<PreServiceCourse> traineeCourses = preServiceTrainee.getPreServiceCourses();
        return traineeCourses.stream()
                .map(preServiceCourseMapper::mapToDto)
                .toList();
    }

    @Transactional
    public PreServiceTraineeResponse updatePreServiceTrainee(UUID tenantId,
                                                             UUID traineeId,
                                                             PreServiceTraineeRequest request,
                                                             MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        if (preServiceTraineeRepository.existsByTenantIdAndTraineeIdAndIdNot(
                tenant.getId(), request.getTraineeId(), preServiceTrainee.getId())) {
            throw new ResourceExistsException(
                    "Trainee with trainee Id '" + request.getTraineeId() + "' already exists");
        }
        preServiceTrainee = preServiceTraineeMapper.updateEntity(tenant, preServiceTrainee, request, file);
        preServiceTrainee = preServiceTraineeRepository.save(preServiceTrainee);
        return preServiceTraineeMapper.mapToDto(preServiceTrainee);
    }

    @Transactional
    public void deletePreServiceTrainee(UUID tenantId,
                                        UUID traineeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        Set<CheckedDocument> checkedDocuments = preServiceTrainee.getCheckedDocuments();
        preServiceTrainee.getCheckedDocuments().removeAll(checkedDocuments);
        preServiceTraineeRepository.delete(preServiceTrainee);
    }
}