package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.AssessmentWeightRequest;
import com.saas.recruitment.dto.response.AssessmentWeightResponse;
import com.saas.recruitment.service.AssessmentWeightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/assessment-weights/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Assessment Weight")
public class AssessmentWeightController {

    private final AssessmentWeightService assessmentWeightService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createAssessmentWeight(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody AssessmentWeightRequest request) {

        permissionEvaluator.addAssessmentWeightPermission(tenantId);

        AssessmentWeightResponse response = assessmentWeightService
                .createAssessmentWeight(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAssessmentWeights(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllAssessmentWeightsPermission(tenantId);

        List<AssessmentWeightResponse> responses = assessmentWeightService
                .getAllAssessmentWeights(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{assessment-weight-id}")
    public ResponseEntity<?> getAssessmentWeightById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("assessment-weight-id") UUID assessmentWeightId) {

        permissionEvaluator.getAssessmentWeightByIdPermission(tenantId);

        AssessmentWeightResponse response = assessmentWeightService
                .getAssessmentWeightById(tenantId, assessmentWeightId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/recruitment/{recruitment-id}")
    public ResponseEntity<?> getAssessmentWeightByRecruitmentId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getAssessmentWeightByRecruitmentIdPermission(tenantId);

        AssessmentWeightResponse response = assessmentWeightService
                .getAssessmentWeightByRecruitmentId(tenantId, recruitmentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{assessment-weight-id}")
    public ResponseEntity<?> updateAssessmentWeight(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("assessment-weight-id") UUID assessmentWeightId,
            @Valid @RequestBody AssessmentWeightRequest request) {

        permissionEvaluator.updateAssessmentWeightPermission(tenantId);

        AssessmentWeightResponse response = assessmentWeightService
                .updateAssessmentWeight(tenantId, assessmentWeightId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{assessment-weight-id}")
    public ResponseEntity<?> deleteAssessmentWeight(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("assessment-weight-id") UUID assessmentWeightId) {

        permissionEvaluator.deleteAssessmentWeightPermission(tenantId);

        assessmentWeightService.deleteAssessmentWeight(tenantId, assessmentWeightId);
        return ResponseEntity.ok("Assessment weight deleted successfully!");
    }
}