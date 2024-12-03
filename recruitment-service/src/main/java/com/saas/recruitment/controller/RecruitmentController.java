package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.RecruitmentApproveRequest;
import com.saas.recruitment.dto.request.RecruitmentRequest;
import com.saas.recruitment.dto.response.RecruitmentResponse;
import com.saas.recruitment.enums.RecruitmentMode;
import com.saas.recruitment.enums.RecruitmentStatus;
import com.saas.recruitment.service.RecruitmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/recruitments/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody RecruitmentRequest request) {

        permissionEvaluator.addRecruitmentPermission(tenantId);

        RecruitmentResponse response = recruitmentService
                .createRecruitment(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllRecruitments(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllRecruitmentsPermission(tenantId);

        List<RecruitmentResponse> responses = recruitmentService
                .getAllRecruitments(tenantId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/get/{recruitment-id}")
    public ResponseEntity<?> getRecruitmentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getRecruitmentByIdPermission(tenantId);

        RecruitmentResponse response = recruitmentService
                .getRecruitmentById(tenantId, recruitmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/vacancy")
    public ResponseEntity<?> getRecruitmentByVacancyNumber(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("vacancy-number") String vacancyNumber) {

        permissionEvaluator.getRecruitmentByVacancyNumberPermission(tenantId);

        RecruitmentResponse response = recruitmentService
                .getRecruitmentByVacancyNumber(tenantId, vacancyNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/status")
    public ResponseEntity<?> getRecruitmentsByStatus(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("recruitment-status") RecruitmentStatus recruitmentStatus) {

        permissionEvaluator.getRecruitmentsByStatusPermission(tenantId);

        List<RecruitmentResponse> responses = recruitmentService
                .getRecruitmentsByStatus(tenantId, recruitmentStatus);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/get/mode")
    public ResponseEntity<?> getRecruitmentsByMode(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("recruitment-mode") String recruitmentMode) {

        permissionEvaluator.getRecruitmentsByStatusPermission(tenantId);

        List<RecruitmentResponse> responses = recruitmentService
                .getRecruitmentsByMode(tenantId, RecruitmentMode.valueOf(recruitmentMode));
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/update/{recruitment-id}")
    public ResponseEntity<?> updateRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @Valid @RequestBody RecruitmentRequest request) {

        permissionEvaluator.updateRecruitmentPermission(tenantId);

        RecruitmentResponse response = recruitmentService
                .updateRecruitment(tenantId, recruitmentId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/approve/{recruitment-id}")
    public ResponseEntity<?> approveRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @Valid @RequestBody RecruitmentApproveRequest request) {

        permissionEvaluator.approveRecruitmentPermission(tenantId);

        RecruitmentResponse response = recruitmentService
                .approveRecruitment(tenantId, recruitmentId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{recruitment-id}")
    public ResponseEntity<?> deleteRecruitment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.deleteRecruitmentPermission(tenantId);

        recruitmentService.deleteRecruitment(tenantId, recruitmentId);
        return ResponseEntity.ok("Recruitment deleted successfully");
    }
}