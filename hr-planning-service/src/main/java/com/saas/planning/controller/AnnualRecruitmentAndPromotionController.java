package com.saas.planning.controller;

import com.saas.planning.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.saas.planning.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.saas.planning.service.AnnualRecruitmentAndPromotionService;
import com.saas.planning.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/annual-recruitment-promotion/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Annual Recruitment and Promotion")
public class AnnualRecruitmentAndPromotionController {

    private final AnnualRecruitmentAndPromotionService annualRecruitmentAndPromotionService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/create")
    public ResponseEntity<AnnualRecruitmentAndPromotionResponse> createAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody AnnualRecruitmentAndPromotionRequest request
    ) {

        permissionEvaluator.addAnnualRecruitmentAndPromotionPermission(tenantId);

        AnnualRecruitmentAndPromotionResponse createdEntity = annualRecruitmentAndPromotionService
                .createAnnualRecruitmentAndPromotion(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnnualRecruitmentAndPromotionResponse>> getAllAnnualRecruitmentAndPromotions(
            @PathVariable("tenantId") UUID tenantId
    ) {

        permissionEvaluator.getAllAnnualRecruitmentAndPromotionsPermission(tenantId);

        List<AnnualRecruitmentAndPromotionResponse> allEntities = annualRecruitmentAndPromotionService
                .getAllAnnualRecruitmentAndPromotions(tenantId);
        return ResponseEntity.ok(allEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnualRecruitmentAndPromotionResponse> getAnnualRecruitmentAndPromotionById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id
    ) {

        permissionEvaluator.getAnnualRecruitmentAndPromotionByIdPermission(tenantId);

        AnnualRecruitmentAndPromotionResponse entity = annualRecruitmentAndPromotionService
                .getAnnualRecruitmentAndPromotionById(tenantId, id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<AnnualRecruitmentAndPromotionResponse> updateAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @Valid @RequestBody AnnualRecruitmentAndPromotionRequest request
    ) {

        permissionEvaluator.updateAnnualRecruitmentAndPromotionPermission(tenantId);

        AnnualRecruitmentAndPromotionResponse updatedEntity = annualRecruitmentAndPromotionService
                .updateAnnualRecruitmentAndPromotion(tenantId, id, request);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteAnnualRecruitmentAndPromotion(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id
    ) {

        permissionEvaluator.deleteAnnualRecruitmentAndPromotionPermission(tenantId);

        annualRecruitmentAndPromotionService.deleteAnnualRecruitmentAndPromotion(tenantId, id);
        return ResponseEntity.ok("Annual recruitment and promotion entry deleted successfully!");
    }
}
