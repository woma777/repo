package com.saas.promotion.controller;

import com.saas.promotion.utility.PermissionEvaluator;
import com.saas.promotion.dto.request.CandidateEvaluationRequest;
import com.saas.promotion.dto.response.CandidateEvaluationResponse;
import com.saas.promotion.service.CandidateEvaluationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/promotion/evaluations/{tenantId}/{candidateId}")
@RequiredArgsConstructor
@Tag(name = "Candidate Evaluation")
public class CandidateEvaluationController {

    private final CandidateEvaluationService evaluationService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCandidateEvaluation(
            @PathVariable UUID tenantId,
            @PathVariable UUID candidateId,
            @Valid @RequestBody CandidateEvaluationRequest request) {

        permissionEvaluator.addCandidateEvaluationPermission(tenantId);

        CandidateEvaluationResponse response = evaluationService
                .addCandidateEvaluation(tenantId, candidateId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllCandidateEvaluations(
            @PathVariable UUID tenantId,
            @PathVariable UUID candidateId) {

        permissionEvaluator.getAllCandidateEvaluationsPermission(tenantId);

        List<CandidateEvaluationResponse> responses = evaluationService
                .getAllCandidateEvaluations(tenantId, candidateId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{evaluationId}")
    public ResponseEntity<?> getCandidateEvaluationById(
            @PathVariable UUID tenantId,
            @PathVariable UUID candidateId,
            @PathVariable UUID evaluationId) {

        permissionEvaluator.getCandidateEvaluationByIdPermission(tenantId);

        CandidateEvaluationResponse response = evaluationService
                .getCandidateEvaluationById(tenantId, candidateId, evaluationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{evaluationId}")
    public ResponseEntity<?> updateCandidateEvaluation(
            @PathVariable UUID tenantId,
            @PathVariable UUID candidateId,
            @PathVariable UUID evaluationId,
            @RequestParam double result) {

        permissionEvaluator.updateCandidateEvaluationPermission(tenantId);

        CandidateEvaluationResponse response = evaluationService
                .updateCandidateEvaluation(tenantId, candidateId, evaluationId, result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{evaluationId}")
    public ResponseEntity<?> deleteCandidateEvaluation(
            @PathVariable UUID tenantId,
            @PathVariable UUID candidateId,
            @PathVariable UUID evaluationId) {

        permissionEvaluator.deleteCandidateEvaluationPermission(tenantId);

        evaluationService.deleteCandidateEvaluation(tenantId, candidateId, evaluationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Candidate evaluation deleted successfully!");
    }
}
