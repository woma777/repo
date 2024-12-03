package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.StaffPlanRequest;
import com.saas.organization.dto.responseDto.StaffPlanResponse;
import com.saas.organization.service.StaffPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/staff-plans/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Staff Plan")
public class StaffPlanController {

    private final StaffPlanService staffPlanService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-staff-plan")
    public ResponseEntity<?> createStaffPlan(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody StaffPlanRequest staffPlanRequest) {

        permissionEvaluator.addStaffPlanPermission(tenantId);

        StaffPlanResponse staffPlan = staffPlanService
                .createStaffPlan(tenantId, staffPlanRequest);
        return new ResponseEntity<>(staffPlan, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStaffPlans(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllStaffPlansPermission(tenantId);

        List<StaffPlanResponse> staffPlans = staffPlanService.getAllStaffPlans(tenantId);
        return ResponseEntity.ok(staffPlans);
    }

    @GetMapping("/get/{staffPlanId}")
    public ResponseEntity<?> getStaffPlanById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID staffPlanId) {

        permissionEvaluator.getStaffPlanByIdPermission(tenantId);

        StaffPlanResponse staffPlan = staffPlanService.getStaffPlanById(tenantId, staffPlanId);
        return ResponseEntity.ok(staffPlan);
    }

    @GetMapping("/departments/{department-id}")
    public ResponseEntity<?> getStaffPlanByDepartmentId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("department-id") UUID departmentId) {

        permissionEvaluator.getStaffPlansByDepartmentIdPermission(tenantId);

        List<StaffPlanResponse> response = staffPlanService
                .getStaffPlanByDepartmentId(departmentId, tenantId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-staff-plan/{id}")
    public ResponseEntity<?> updateStaffPlan(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody StaffPlanRequest staffPlanRequest) {

        permissionEvaluator.updateStaffPlanPermission(tenantId);

        StaffPlanResponse staffPlan = staffPlanService
                .updateStaffPlan(id, tenantId, staffPlanRequest);
        return ResponseEntity.ok(staffPlan);
    }

    @DeleteMapping("/delete-staff-plan/{id}")
    public ResponseEntity<?> deleteStaffPlan(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteStaffPlanPermission(tenantId);

        staffPlanService.deleteStaffPlan(id, tenantId);
        return ResponseEntity.ok("Staff-plan deleted successfully!");
    }
}