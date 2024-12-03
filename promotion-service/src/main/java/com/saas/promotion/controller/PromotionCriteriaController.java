package com.saas.promotion.controller;

import com.saas.promotion.utility.PermissionEvaluator;
import com.saas.promotion.dto.request.PromotionCriteriaRequest;
import com.saas.promotion.dto.response.PromotionCriteriaResponse;
import com.saas.promotion.service.PromotionCriteriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/promotion/promotion-criteria/{tenantId}")
@Tag(name = "Promotion Criteria")
@RequiredArgsConstructor
public class PromotionCriteriaController {

    private final PromotionCriteriaService promotionCriteriaService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addPromotionCriteria(
            @PathVariable UUID tenantId,
            @Valid @RequestBody PromotionCriteriaRequest request) {

        permissionEvaluator.addPromotionCriteriaPermission(tenantId);

        PromotionCriteriaResponse response = promotionCriteriaService
                .addPromotionCriteria(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sub-criteria/{parentId}/add")
    public ResponseEntity<?> addSubPromotionCriteria(
            @PathVariable UUID tenantId,
            @PathVariable UUID parentId,
            @RequestBody PromotionCriteriaRequest request) {

        permissionEvaluator.addPromotionCriteriaPermission(tenantId);

        PromotionCriteriaResponse response = promotionCriteriaService
                .addSubPromotionCriteria(tenantId, parentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPromotionCriteria(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllPromotionCriteriaPermission(tenantId);

        List<PromotionCriteriaResponse> responses = promotionCriteriaService
                .getAllPromotionCriteria(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/sub-criteria/{parentId}")
    public ResponseEntity<?> getAllSubPromotionCriteria(
            @PathVariable UUID tenantId,
            @PathVariable UUID parentId) {

        permissionEvaluator.getAllPromotionCriteriaPermission(tenantId);

        List<PromotionCriteriaResponse> responses = promotionCriteriaService
                .getAllSubPromotionCriteria(tenantId, parentId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{promotionCriteriaId}")
    public ResponseEntity<?> getPromotionCriteriaById(
            @PathVariable UUID tenantId,
            @PathVariable UUID promotionCriteriaId) {

        permissionEvaluator.getPromotionCriteriaByIdPermission(tenantId);

        PromotionCriteriaResponse response = promotionCriteriaService
                .getPromotionCriteriaById(tenantId, promotionCriteriaId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{promotionCriteriaId}")
    public ResponseEntity<?> updatePromotionCriteria(
            @PathVariable UUID tenantId,
            @PathVariable UUID promotionCriteriaId,
            @Valid @RequestBody PromotionCriteriaRequest request) {

        permissionEvaluator.updatePromotionCriteriaPermission(tenantId);

        PromotionCriteriaResponse response = promotionCriteriaService
                .updatePromotionCriteria(tenantId, promotionCriteriaId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{promotionCriteriaId}")
    public ResponseEntity<?> deletePromotionCriteria(
            @PathVariable UUID tenantId,
            @PathVariable UUID promotionCriteriaId) {

        permissionEvaluator.deletePromotionCriteriaPermission(tenantId);

        promotionCriteriaService.deletePromotionCriteriaById(tenantId, promotionCriteriaId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Promotion criteria deleted successfully!");
    }
}
