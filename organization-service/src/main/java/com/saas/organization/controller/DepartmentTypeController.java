package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.DepartmentTypeRequest;
import com.saas.organization.dto.responseDto.DepartmentTypeResponse;
import com.saas.organization.service.DepartmentTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/department-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Department Type")
public class DepartmentTypeController {

    private final DepartmentTypeService departmentTypeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-department-type")
    public ResponseEntity<?> createDepartmentType(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody DepartmentTypeRequest departmentTypeRequest) {

        permissionEvaluator.addDepartmentTypePermission(tenantId);

        DepartmentTypeResponse departmentType = departmentTypeService
                .createDepartmentType(tenantId, departmentTypeRequest);
        return new ResponseEntity<>(departmentType, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDepartmentTypes(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllDepartmentTypesPermission(tenantId);

        List<DepartmentTypeResponse> locationTypes = departmentTypeService
                .getAllDepartmentTypes(tenantId);
        return ResponseEntity.ok(locationTypes);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getDepartmentTypeById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getDepartmentTypeByIdPermission(tenantId);

        DepartmentTypeResponse location = departmentTypeService.getDepartmentTypeById(id, tenantId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("/update-departmentType/{id}")
    public ResponseEntity<?> updateDepartmentType(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody DepartmentTypeRequest departmentTypeRequest) {

        permissionEvaluator.updateDepartmentTypePermission(tenantId);

        DepartmentTypeResponse location = departmentTypeService
                .updateDepartmentType(id, tenantId, departmentTypeRequest);
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/delete-departmentType/{id}")
    public ResponseEntity<?> deleteDepartmentType(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.deleteDepartmentTypePermission(tenantId);

        departmentTypeService.deleteDepartmentType(id, tenantId);
        return ResponseEntity.ok("DepartmentType deleted successfully!");
    }
}