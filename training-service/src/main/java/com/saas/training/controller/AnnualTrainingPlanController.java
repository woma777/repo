package com.saas.training.controller;

import com.saas.training.dto.request.AnnualTrainingPlanRequest;
import com.saas.training.dto.response.AnnualTrainingPlanResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.AnnualTrainingPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/annual-training-plans/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Annual Training Plan")
public class AnnualTrainingPlanController {

    private final AnnualTrainingPlanService annualTrainingPlanService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addAnnualTrainingPlan(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody AnnualTrainingPlanRequest request) {

        permissionEvaluator.addAnnualTrainingPlanPermission(tenantId);

        AnnualTrainingPlanResponse response = annualTrainingPlanService
                .addAnnualTrainingPlan(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAnnualTrainingPlans(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllAnnualTrainingPlansPermission(tenantId);

        List<AnnualTrainingPlanResponse> responses = annualTrainingPlanService
                .getAllAnnualTrainingPlans(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{training-plan-id}")
    public ResponseEntity<?> getAnnualTrainingPlanById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-plan-id") UUID trainingPlanId) {

        permissionEvaluator.getAnnualTrainingPlanByIdPermission(tenantId);

        AnnualTrainingPlanResponse response = annualTrainingPlanService
                .getAnnualTrainingPlanById(tenantId, trainingPlanId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/department/{department-id}")
    public ResponseEntity<?> getAnnualTrainingPlansByDepartmentId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("department-id") UUID departmentId) {

        permissionEvaluator.getAnnualTrainingPlansByDepartmentIdPermission(tenantId);

        List<AnnualTrainingPlanResponse> responses = annualTrainingPlanService
                .getAnnualTrainingPlansByDepartmentId(tenantId, departmentId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/update/{training-plan-id}")
    public ResponseEntity<?> updateAnnualTrainingPlan(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-plan-id") UUID trainingPlanId,
            @Valid @RequestBody AnnualTrainingPlanRequest request) {

        permissionEvaluator.updateAnnualTrainingPlanPermission(tenantId);

        AnnualTrainingPlanResponse response = annualTrainingPlanService
                .updateAnnualTrainingPlan(tenantId, trainingPlanId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{training-plan-id}")
    public ResponseEntity<?> deleteAnnualTrainingPlan(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("training-plan-id") UUID trainingPlanId) {

        permissionEvaluator.deleteAnnualTrainingPlanPermission(tenantId);

        annualTrainingPlanService.deleteAnnualTrainingPlan(tenantId, trainingPlanId);
        return ResponseEntity.ok("Annual training plan deleted successfully");
    }
}