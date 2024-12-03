package com.saas.training.controller;

import com.saas.training.dto.request.TrainingParticipantRequest;
import com.saas.training.dto.response.TrainingParticipantResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.TrainingParticipantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/training-participants/{tenant-id}/{training-id}")
@RequiredArgsConstructor
@Tag(name = "Training participant")
public class TrainingParticipantController {

    private final TrainingParticipantService trainingParticipantService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTrainingParticipant(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId,
            @Valid @RequestBody TrainingParticipantRequest request) {

        permissionEvaluator.addTrainingParticipantPermission(tenantId);

        TrainingParticipantResponse response = trainingParticipantService
                .addTrainingParticipant(tenantId, trainingId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTrainingParticipants(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId) {

        permissionEvaluator.getAllTrainingParticipantsPermission(tenantId);

        List<TrainingParticipantResponse> responses = trainingParticipantService
                .getAllTrainingParticipants(tenantId, trainingId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{participant-id}")
    public ResponseEntity<?> getTrainingParticipantById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId,
            @PathVariable("participant-id") UUID participantId) {

        permissionEvaluator.getTrainingParticipantByIdPermission(tenantId);

        TrainingParticipantResponse response = trainingParticipantService
                .getTrainingParticipantById(tenantId, trainingId, participantId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{participant-id}")
    public ResponseEntity<?> updateTrainingParticipant(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId,
            @PathVariable("participant-id") UUID participantId,
            @Valid @RequestBody TrainingParticipantRequest request) {

        permissionEvaluator.updateTrainingParticipantPermission(tenantId);

        TrainingParticipantResponse response = trainingParticipantService
                .updateTrainingParticipant(tenantId, trainingId, participantId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{participant-id}")
    public ResponseEntity<?> deleteTrainingParticipant(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-id") UUID trainingId,
            @PathVariable("participant-id") UUID participantId) {

        permissionEvaluator.deleteTrainingParticipantPermission(tenantId);

        trainingParticipantService.deleteTrainingParticipant(tenantId, trainingId, participantId);
        return ResponseEntity.ok("Training participant deleted successfully");
    }
}