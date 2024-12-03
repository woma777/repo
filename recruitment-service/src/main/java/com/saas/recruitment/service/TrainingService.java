package com.saas.recruitment.service;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.TrainingRequest;
import com.saas.recruitment.dto.response.TrainingResponse;
import com.saas.recruitment.model.Applicant;
import com.saas.recruitment.model.Training;
import com.saas.recruitment.exception.ResourceNotFoundException;
import com.saas.recruitment.mapper.TrainingMapper;
import com.saas.recruitment.repository.TrainingRepository;
import com.saas.recruitment.utility.FileUtil;
import com.saas.recruitment.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingResponse addTraining(UUID tenantId,
                                        UUID applicantId,
                                        TrainingRequest request,
                                        MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Training training = trainingMapper.mapToEntity(tenant, applicant, request, file);
        training = trainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    public List<TrainingResponse> getAllTrainings(UUID tenantId,
                                                  UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        List<Training> trainings = trainingRepository
                .findByTenantIdAndApplicant(tenant.getId(), applicant);
        return trainings.stream()
                .map(trainingMapper::mapToDto)
                .toList();
    }

    public TrainingResponse getTrainingById(UUID tenantId,
                                            UUID applicantId,
                                            UUID trainingId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Training training = getTrainingById(tenant.getId(), applicant, trainingId);
        return trainingMapper.mapToDto(training);
    }

    public byte[] getTrainingCertificateById(UUID tenantId,
                                             UUID applicantId,
                                             UUID trainingId,
                                             MediaType mediaType) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Training training = getTrainingById(tenant.getId(), applicant, trainingId);
        switch (training.getFileType()) {
            case "application/pdf":
                mediaType = MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE);
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                mediaType = MediaType.valueOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
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
                throw new IllegalArgumentException("Unsupported file type: " + training.getFileType());
        }
        byte[] fileBytes = FileUtil.decompressFile(training.getFileBytes());
        if (fileBytes.length == 0) {
            throw new ResourceNotFoundException("Training file is not available");
        }
        return fileBytes;
    }

    @Transactional
    public TrainingResponse updateTraining(UUID tenantId,
                                           UUID applicantId,
                                           UUID trainingId,
                                           TrainingRequest request,
                                           MultipartFile file) throws IOException {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Training training = getTrainingById(tenant.getId(), applicant, trainingId);
        training = trainingMapper.updateTraining(training, request, file);
        training = trainingRepository.save(training);
        return trainingMapper.mapToDto(training);
    }

    @Transactional
    public void deleteTraining(UUID tenantId,
                               UUID applicantId,
                               UUID trainingId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Training training = getTrainingById(tenant.getId(), applicant, trainingId);
        trainingRepository.delete(training);
    }

    private Training getTrainingById(UUID tenantId, Applicant applicant, UUID trainingId) {

        return trainingRepository.findById(trainingId)
                .filter(tr -> tr.getTenantId().equals(tenantId))
                .filter(tr -> tr.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training not found with id '" + trainingId + "'"));
    }
}