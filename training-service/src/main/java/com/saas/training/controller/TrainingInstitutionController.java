package com.saas.training.controller;

import com.saas.training.dto.request.TrainingInstitutionRequest;
import com.saas.training.dto.response.TrainingInstitutionResponse;
import com.saas.training.service.TrainingInstitutionService;
import com.saas.training.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/training-institutions/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Training Institution")
public class TrainingInstitutionController {

    private final TrainingInstitutionService trainingInstitutionService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingInstitution(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody TrainingInstitutionRequest request) {

        permissionEvaluator.addInstitutionPermission(tenantId);

        TrainingInstitutionResponse response = trainingInstitutionService
                .addTrainingInstitution(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainingInstitutions(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllInstitutionsPermission(tenantId);

        List<TrainingInstitutionResponse> responses = trainingInstitutionService
                .getAllTrainingInstitutions(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{institution-id}")
    public ResponseEntity<?> getTrainingInstitutionById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("institution-id") UUID institutionId) {

        permissionEvaluator.getInstitutionByIdPermission(tenantId);

        TrainingInstitutionResponse response = trainingInstitutionService
                .getTrainingInstitutionById(tenantId, institutionId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{institution-id}")
    public ResponseEntity<?> updateTrainingInstitution(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("institution-id") UUID institutionId,
            @Valid @RequestBody TrainingInstitutionRequest request) {

        permissionEvaluator.updateInstitutionPermission(tenantId);

        TrainingInstitutionResponse response = trainingInstitutionService
                .updateTrainingInstitution(tenantId, institutionId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{institution-id}")
    public ResponseEntity<?> deleteTrainingInstitution(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("institution-id") UUID institutionId) {

        permissionEvaluator.deleteInstitutionPermission(tenantId);

        trainingInstitutionService.deleteTrainingInstitution(tenantId, institutionId);
        return ResponseEntity.ok("Training institution deleted successfully");
    }
}