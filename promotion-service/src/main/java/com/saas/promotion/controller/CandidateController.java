package com.saas.promotion.controller;

import com.saas.promotion.utility.PermissionEvaluator;
import com.saas.promotion.dto.request.CandidateRequest;
import com.saas.promotion.dto.response.CandidateResponse;
import com.saas.promotion.service.CandidateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/promotion/candidates/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Promotion Candidate")
public class CandidateController {

    private final CandidateService candidateService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCandidate(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody CandidateRequest request) {

        permissionEvaluator.addCandidatePermission(tenantId);

        CandidateResponse response = candidateService
                .addCandidate(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCandidates(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllCandidatesPermission(tenantId);

        List<CandidateResponse> response = candidateService
                .getAllCandidates(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/{promotionId}")
    public ResponseEntity<?> getCandidateById(
            @PathVariable UUID tenantId,
            @PathVariable UUID promotionId) {

        permissionEvaluator.getCandidateByIdPermission(tenantId);

        CandidateResponse response = candidateService
                .getCandidateById(tenantId, promotionId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{promotionId}")
    public ResponseEntity<?> updateCandidate(
            @PathVariable UUID tenantId,
            @PathVariable UUID promotionId,
            @Valid @RequestBody CandidateRequest request) {

        permissionEvaluator.updateCandidatePermission(tenantId);

        CandidateResponse response = candidateService
                .updateCandidate(tenantId, promotionId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{promotionId}")
    public ResponseEntity<?> deleteCandidate(
            @PathVariable UUID tenantId,
            @PathVariable UUID promotionId) {

        permissionEvaluator.deleteCandidatePermission(tenantId);

        candidateService.deleteCandidate(tenantId, promotionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Applied Promotion deleted successfully!");
    }
}
