package com.saas.training.controller;

import com.saas.training.dto.request.PreServiceCourseTypeRequest;
import com.saas.training.dto.response.PreServiceCourseTypeResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.PreServiceCourseTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/course-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Pre-Service Course Type")
public class PreServiceCourseTypeController {

    private final PreServiceCourseTypeService preServiceCourseTypeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCourseType(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody PreServiceCourseTypeRequest request) {

        permissionEvaluator.addPreServiceCourseTypePermission(tenantId);

        PreServiceCourseTypeResponse response = preServiceCourseTypeService
                .addCourseType(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCourseTypes(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllPreServiceCourseTypesPermission(tenantId);

        List<PreServiceCourseTypeResponse> responses = preServiceCourseTypeService
                .getAllCourseTypes(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{course-type-id}")
    public ResponseEntity<?> getCourseTypeById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-type-id") UUID courseTypeId) {

        permissionEvaluator.getPreServiceCourseTypeByIdPermission(tenantId);

        PreServiceCourseTypeResponse response = preServiceCourseTypeService
                .getCourseTypeById(tenantId, courseTypeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{course-type-id}")
    public ResponseEntity<?> updateCourseType(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-type-id") UUID courseTypeId,
            @Valid @RequestBody PreServiceCourseTypeRequest request) {

        permissionEvaluator.updatePreServiceCourseTypePermission(tenantId);

        PreServiceCourseTypeResponse response = preServiceCourseTypeService
                .updateCourseType(tenantId, courseTypeId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{course-type-id}")
    public ResponseEntity<?> deleteCourseType(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("course-type-id") UUID courseTypeId) {

        permissionEvaluator.deletePreServiceCourseTypePermission(tenantId);

        preServiceCourseTypeService.deleteCourseType(tenantId, courseTypeId);
        return ResponseEntity.ok("Course type deleted successfully!");
    }
}