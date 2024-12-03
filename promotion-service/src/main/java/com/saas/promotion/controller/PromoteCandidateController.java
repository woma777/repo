package com.saas.promotion.controller;

import com.saas.promotion.utility.PermissionEvaluator;
import com.saas.promotion.dto.request.PromoteCandidateRequest;
import com.saas.promotion.dto.response.PromoteCandidateResponse;
import com.saas.promotion.service.PromoteCandidateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/promotion/promote-candidates/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Promote Candidate")
public class PromoteCandidateController {

    private final PromoteCandidateService promoteCandidateService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addPromoteCandidate(
            @PathVariable UUID tenantId,
            @Valid @RequestBody PromoteCandidateRequest request) {

        permissionEvaluator.addPromoteCandidatePermission(tenantId);

        PromoteCandidateResponse response = promoteCandidateService
                .addPromoteCandidate(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPromoteCandidates(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllPromoteCandidatesPermission(tenantId);

        List<PromoteCandidateResponse> responses = promoteCandidateService
                .getAllPromoteCandidates(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{promoteId}")
    public ResponseEntity<?> getPromoteCandidateById(
            @PathVariable UUID tenantId,
            @PathVariable UUID promoteId) {

        permissionEvaluator.getPromoteCandidateByIdPermission(tenantId);

        PromoteCandidateResponse response = promoteCandidateService
                .getPromoteCandidateById(tenantId, promoteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{promoteId}")
    public ResponseEntity<?> updatePromoteCandidate(
            @PathVariable UUID tenantId,
            @PathVariable UUID promoteId,
            @RequestParam UUID payGradeId) {

        permissionEvaluator.updatePromoteCandidatePermission(tenantId);

        PromoteCandidateResponse response = promoteCandidateService
                .updatePromoteCandidate(tenantId, promoteId, payGradeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{promoteId}")
    public ResponseEntity<?> deletePromoteCandidate(
            @PathVariable UUID tenantId,
            @PathVariable UUID promoteId) {

        permissionEvaluator.deletePromoteCandidatePermission(tenantId);

        promoteCandidateService.deletePromoteCandidate(tenantId, promoteId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Promote candidate deleted successfully!");
    }
}
