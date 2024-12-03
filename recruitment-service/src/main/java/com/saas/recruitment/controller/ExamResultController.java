package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.ExamResultRequest;
import com.saas.recruitment.dto.response.ExamResultResponse;
import com.saas.recruitment.service.ExamResultService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/exam-result/{tenant-id}/{recruitment-id}")
@RequiredArgsConstructor
@Tag(name = "Exam Result")
public class ExamResultController {

    private final ExamResultService examResultService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{applicant-id}/add")
    public ResponseEntity<?> createExamResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @PathVariable("applicant-id") UUID applicantId,
            @Valid @RequestBody ExamResultRequest request) {

        permissionEvaluator.addExamResultPermission(tenantId);

        ExamResultResponse response = examResultService
                .createExamResult(tenantId, recruitmentId, applicantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllExamResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getAllExamResultsPermission(tenantId);

        List<ExamResultResponse> responses = examResultService
                .getAllExamResult(tenantId, recruitmentId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{applicant-id}/get/{exam-result-id}")
    public ResponseEntity<?> getExamResultById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("exam-result-id") UUID examResultId) {

        permissionEvaluator.getExamResultByIdPermission(tenantId);

        ExamResultResponse response = examResultService
                .getExamResultById(tenantId, recruitmentId, applicantId, examResultId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{applicant-id}/update/{exam-result-id}")
    public ResponseEntity<?> updateExamResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("exam-result-id") UUID examResultId,
            @Valid @RequestBody ExamResultRequest request) {

        permissionEvaluator.updateExamResultPermission(tenantId);

        ExamResultResponse response = examResultService
                .updateExamResult(tenantId, recruitmentId, applicantId, examResultId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{applicant-id}/delete/{exam-result-id}")
    public ResponseEntity<?> deleteExamResult(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("exam-result-id") UUID examResultId) {

        permissionEvaluator.deleteExamResultPermission(tenantId);

        examResultService
                .deleteExamResult(tenantId, recruitmentId, applicantId, examResultId);
        return ResponseEntity.ok("Exam result deleted successfully!");
    }
}