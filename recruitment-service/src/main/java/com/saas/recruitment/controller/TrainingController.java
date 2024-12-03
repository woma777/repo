package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.TrainingRequest;
import com.saas.recruitment.dto.response.TrainingResponse;
import com.saas.recruitment.service.TrainingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/applicant-trainings/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Training")
public class TrainingController {

    private final TrainingService trainingService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @Valid @RequestPart("training") TrainingRequest request,
            @RequestPart("certificate") MultipartFile file) throws IOException {

        permissionEvaluator.addTrainingPermission(tenantId);

        TrainingResponse training = trainingService
                .addTraining(tenantId, applicantId, request, file);
        return new ResponseEntity<>(training, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainings(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId) {

        permissionEvaluator.getAllTrainingsPermission(tenantId);

        List<TrainingResponse> trainings = trainingService
                .getAllTrainings(tenantId, applicantId);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/get/{training-id}")
    public ResponseEntity<?> getTrainingById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.getTrainingByIdPermission(tenantId);

        TrainingResponse training = trainingService
                .getTrainingById(tenantId, applicantId, trainingId);
        return ResponseEntity.ok(training);
    }

    @GetMapping("/download-certificate/{training-id}")
    public ResponseEntity<?> getTrainingCertificateById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.downloadTrainingCertificatePermission(tenantId);

        TrainingResponse training = trainingService
                .getTrainingById(tenantId, applicantId, trainingId);
        if (training == null || training.getCertificateType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Applicant training certificate not found");
        }
        MediaType mediaType = MediaType.valueOf(training.getCertificateType());
        byte[] documentBytes = trainingService
                .getTrainingCertificateById(tenantId, applicantId, trainingId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
    }

    @PutMapping("/update/{training-id}")
    public ResponseEntity<?> updateTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("training-id") UUID trainingId,
            @Valid @RequestPart(value = "training", required = false) TrainingRequest request,
            @RequestPart(value = "certificate", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateTrainingPermission(tenantId);

        TrainingResponse training = trainingService
                .updateTraining(tenantId, applicantId, trainingId, request, file);
        return ResponseEntity.ok(training);
    }

    @DeleteMapping("/delete/{training-id}")
    public ResponseEntity<?> deleteTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.deleteTrainingPermission(tenantId);

        trainingService.deleteTraining(tenantId, applicantId, trainingId);
        return ResponseEntity.ok("Training deleted successfully");
    }
}