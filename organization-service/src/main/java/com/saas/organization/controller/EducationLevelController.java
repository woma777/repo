package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.EducationLevelRequest;
import com.saas.organization.dto.responseDto.EducationLevelResponse;
import com.saas.organization.service.EducationLevelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/education-levels/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Education Level")
public class EducationLevelController {

    private final EducationLevelService educationLevelService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createEducationLevel(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody EducationLevelRequest educationLevelRequest) {

        permissionEvaluator.addEducationLevelPermission(tenantId);

        EducationLevelResponse educationLevel = educationLevelService
                .createEducationLevel(tenantId, educationLevelRequest);
        return new ResponseEntity<>(educationLevel, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEducationLevels(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllEducationLevelsPermission(tenantId);

        List<EducationLevelResponse> educationLevels = educationLevelService
                .getAllEducationLevels(tenantId);
        return ResponseEntity.ok(educationLevels);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEducationLevelById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getEducationLevelByIdPermission(tenantId);

        EducationLevelResponse educationLevel = educationLevelService
                .getEducationLevelById(id, tenantId);
        return ResponseEntity.ok(educationLevel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEducationLevel(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody EducationLevelRequest educationLevelRequest) {

        permissionEvaluator.updateEducationLevelPermission(tenantId);

        EducationLevelResponse educationLevel = educationLevelService
                .updateEducationLevel(id, tenantId, educationLevelRequest);
        return ResponseEntity.ok(educationLevel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEducationLevel(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.deleteEducationLevelPermission(tenantId);

        educationLevelService.deleteEducationLevel(id, tenantId);
        return ResponseEntity.ok("Education Level deleted successfully!");
    }
}