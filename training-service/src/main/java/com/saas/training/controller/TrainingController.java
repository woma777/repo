package com.saas.training.controller;

import com.saas.training.dto.request.TrainingApproveRequest;
import com.saas.training.dto.request.TrainingRequest;
import com.saas.training.dto.response.TrainingResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.TrainingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/trainings/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training")
public class TrainingController {

    private final TrainingService trainingService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody TrainingRequest request) {

        permissionEvaluator.addTrainingPermission(tenantId);

        TrainingResponse response = trainingService
                .addTraining(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainings(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllTrainingsPermission(tenantId);

        List<TrainingResponse> responses = trainingService
                .getAllTrainings(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{training-id}")
    public ResponseEntity<?> getTrainingById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.getTrainingByIdPermission(tenantId);

        TrainingResponse response = trainingService
                .getTrainingById(tenantId, trainingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/status")
    public ResponseEntity<?> getTrainingsByStatus(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("training-status") String trainingStatus) {

        permissionEvaluator.getTrainingsByStatusPermission(tenantId);

        List<TrainingResponse> responses = trainingService
                .getTrainingsByStatus(tenantId, trainingStatus);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/update/{training-id}")
    public ResponseEntity<?> updateTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId,
            @RequestBody TrainingRequest request) {

        permissionEvaluator.updateTrainingPermission(tenantId);

        TrainingResponse response = trainingService
                .updateTraining(tenantId, trainingId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/approve/{training-id}")
    public ResponseEntity<?> approveTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId,
            @Valid @RequestBody TrainingApproveRequest request) {

        permissionEvaluator.approveTrainingPermission(tenantId);

        TrainingResponse response = trainingService
                .approveTraining(tenantId, trainingId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{training-id}")
    public ResponseEntity<?> deleteTraining(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.deleteTrainingPermission(tenantId);

        trainingService.deleteTraining(tenantId, trainingId);
        return ResponseEntity.ok("Training deleted successfully");
    }
}