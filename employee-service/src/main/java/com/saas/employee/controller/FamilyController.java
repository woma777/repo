package com.saas.employee.controller;

import com.saas.employee.utility.PermissionEvaluator;
import com.saas.employee.dto.request.FamilyRequest;
import com.saas.employee.dto.response.FamilyResponse;
import com.saas.employee.service.FamilyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/families/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Family")
public class FamilyController {

    private final FamilyService familyService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addFamily(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @Valid @RequestBody FamilyRequest familyRequest) {

        permissionEvaluator.addFamilyPermission(tenantId, employeeId);

        FamilyResponse family = familyService
                .addFamily(tenantId, employeeId, familyRequest);
        return new ResponseEntity<>(family, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllFamilies(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllFamiliesPermission(tenantId, employeeId);

        List<FamilyResponse> families = familyService
                .getAllFamilies(tenantId, employeeId);
        return ResponseEntity.ok(families);
    }

    @GetMapping("/get/employee-families")
    public ResponseEntity<?> getEmployeeFamilies(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getFamiliesByEmployeeIdPermission(tenantId);

        List<FamilyResponse> families = familyService
                .getEmployeeFamilies(tenantId, employeeId);
        return ResponseEntity.ok(families);
    }

    @GetMapping("/{employee-id}/get/{family-id}")
    public ResponseEntity<?> getFamilyById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("family-id") UUID familyId) {

        permissionEvaluator.getFamilyByIdPermission(tenantId, employeeId);

        FamilyResponse family = familyService
                .getFamilyById(tenantId, employeeId, familyId);
        return ResponseEntity.ok(family);
    }

    @PutMapping("/{employee-id}/update/{family-id}")
    public ResponseEntity<?> updateFamily(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("family-id") UUID familyId,
            @Valid @RequestBody FamilyRequest familyRequest) {

        permissionEvaluator.updateFamilyPermission(tenantId, employeeId);

        FamilyResponse family = familyService
                .updateFamily(tenantId, employeeId, familyId, familyRequest);
        return ResponseEntity.ok(family);
    }

    @DeleteMapping("/{employee-id}/delete/{family-id}")
    public ResponseEntity<?> deleteFamily(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("family-id") UUID familyId) {

        permissionEvaluator.deleteFamilyPermission(tenantId, employeeId);

        familyService.deleteFamily(tenantId, employeeId, familyId);
        return ResponseEntity.ok("Family deleted successfully");
    }
}