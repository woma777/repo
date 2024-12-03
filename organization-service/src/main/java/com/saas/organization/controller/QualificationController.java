package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.QualificationRequest;
import com.saas.organization.dto.responseDto.QualificationResponse;
import com.saas.organization.service.QualificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/qualifications/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Qualification")
public class QualificationController {

    private final QualificationService qualificationService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createQualification(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody QualificationRequest qualificationRequest) {

        permissionEvaluator.addQualificationPermission(tenantId);

        QualificationResponse qualification = qualificationService
                .createQualification(tenantId, qualificationRequest);
        return new ResponseEntity<>(qualification, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllQualifications(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllQualificationsPermission(tenantId);

        List<QualificationResponse> qualifications = qualificationService
                .getAllQualifications(tenantId);
        return ResponseEntity.ok(qualifications);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQualificationById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getQualificationByIdPermission(tenantId);

        QualificationResponse qualification = qualificationService
                .getQualificationById(id, tenantId);
        return ResponseEntity.ok(qualification);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQualification(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody QualificationRequest qualificationRequest) {

        permissionEvaluator.updateQualificationPermission(tenantId);

        QualificationResponse qualification = qualificationService
                .updateQualification(id, tenantId, qualificationRequest);
        return ResponseEntity.ok(qualification);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQualification(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.deleteQualificationPermission(tenantId);

        qualificationService.deleteQualification(id, tenantId);
        return ResponseEntity.ok("Qualification deleted successfully!");
    }
}