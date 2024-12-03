package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.WorkUnitRequest;
import com.saas.organization.dto.responseDto.WorkUnitResponse;
import com.saas.organization.service.WorkUnitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/work-units/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Work Unit")
public class WorkUnitController {

    private final WorkUnitService workUnitService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-work-unit")
    public ResponseEntity<?> createWorkUnit(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody WorkUnitRequest workUnitRequest) {

        permissionEvaluator.addWorkUnitPermission(tenantId);

        WorkUnitResponse workUnit = workUnitService.createWorkUnit(tenantId, workUnitRequest);
        return new ResponseEntity<>(workUnit, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllWorkUnits(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllWorkUnitsPermission(tenantId);

        List<WorkUnitResponse> workUnits = workUnitService.getAllWorkUnits(tenantId);
        return ResponseEntity.ok(workUnits);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getWorkUnitById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getWorkUnitByIdPermission(tenantId);

        WorkUnitResponse workUnit = workUnitService.getWorkUnitById(id, tenantId);
        return ResponseEntity.ok(workUnit);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWorkUnit(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody WorkUnitRequest workUnitRequest) {

        permissionEvaluator.updateWorkUnitPermission(tenantId);

        WorkUnitResponse workUnit = workUnitService.updateWorkUnit(id, tenantId, workUnitRequest);
        return ResponseEntity.ok(workUnit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkUnit(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.deleteWorkUnitPermission(tenantId);

        workUnitService.deleteWorkUnit(id, tenantId);
        return ResponseEntity.ok("WorkUnit deleted successfully!");
    }
}