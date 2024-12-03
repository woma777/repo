package com.saas.employee.controller;

import com.saas.employee.utility.PermissionEvaluator;
import com.saas.employee.dto.request.ReferenceRequest;
import com.saas.employee.dto.response.ReferenceResponse;
import com.saas.employee.service.ReferenceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/references/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Reference")
public class ReferenceController {

    private final ReferenceService referenceService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @Valid @RequestBody ReferenceRequest referenceRequest) {

        permissionEvaluator.addReferencePermission(tenantId, employeeId);

        ReferenceResponse reference = referenceService
                .addReference(tenantId, employeeId, referenceRequest);
        return new ResponseEntity<>(reference, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllReferences(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllReferencesPermission(tenantId, employeeId);

        List<ReferenceResponse> references = referenceService
                .getAllReferences(tenantId, employeeId);
        return ResponseEntity.ok(references);
    }

    @GetMapping("/get/employee-references")
    public ResponseEntity<?> getEmployeeReferences(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getReferencesByEmployeeIdPermission(tenantId);

        List<ReferenceResponse> references = referenceService
                .getEmployeeReferences(tenantId, employeeId);
        return ResponseEntity.ok(references);
    }

    @GetMapping("/{employee-id}/get/{reference-id}")
    public ResponseEntity<?> getReferenceById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("reference-id") UUID referenceId) {

        permissionEvaluator.getReferenceByIdPermission(tenantId, employeeId);

        ReferenceResponse reference = referenceService
                .getReferenceById(tenantId, employeeId, referenceId);
        return ResponseEntity.ok(reference);
    }

    @PutMapping("/{employee-id}/update/{reference-id}")
    public ResponseEntity<?> updateReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("reference-id") UUID referenceId,
            @Valid @RequestBody ReferenceRequest referenceRequest) {

        permissionEvaluator.updateReferencePermission(tenantId, employeeId);

        ReferenceResponse reference = referenceService
                .updateReference(tenantId, employeeId, referenceId, referenceRequest);
        return ResponseEntity.ok(reference);
    }

    @DeleteMapping("/{employee-id}/delete/{reference-id}")
    public ResponseEntity<?> deleteReference(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("reference-id") UUID referenceId) {

        permissionEvaluator.deleteReferencePermission(tenantId, employeeId);

        referenceService.deleteReference(tenantId, employeeId, referenceId);
        return ResponseEntity.ok("Reference deleted successfully");
    }
}