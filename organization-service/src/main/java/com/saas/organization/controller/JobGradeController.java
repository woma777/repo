package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.JobGradeRequestDto;
import com.saas.organization.dto.responseDto.JobGradeResponseDto;
import com.saas.organization.service.JobGradeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/job-grades/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Job Grade")
public class JobGradeController {

    private final JobGradeService jobGradeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createJobGrade(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody JobGradeRequestDto jobGradeRequestDto) {

        permissionEvaluator.addJobGradePermission(tenantId);

        JobGradeResponseDto jobGrade = jobGradeService
                .createJobGrade(tenantId, jobGradeRequestDto);
        return new ResponseEntity<>(jobGrade, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllJobGrades(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllJobGradesPermission(tenantId);

        List<JobGradeResponseDto> jobGrades = jobGradeService.getAllJobGrades(tenantId);
        return ResponseEntity.ok(jobGrades);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getJobGradeById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getJobGradeByIdPermission(tenantId);

        JobGradeResponseDto jobGrade = jobGradeService.getJobGradeById(id, tenantId);
        return ResponseEntity.ok(jobGrade);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJobGrade(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody JobGradeRequestDto jobGradeRequestDto) {

        permissionEvaluator.updateJobGradePermission(tenantId);

        JobGradeResponseDto jobGrade = jobGradeService
                .updateJobGrade(id, tenantId, jobGradeRequestDto);
        return ResponseEntity.ok(jobGrade);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobGrade(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.deleteJobGradePermission(tenantId);

        jobGradeService.deleteJobGrade(id, tenantId);
        return ResponseEntity.ok("Job Grade deleted successfully!");
    }
}