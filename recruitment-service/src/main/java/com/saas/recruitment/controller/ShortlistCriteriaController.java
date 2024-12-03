package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.ShortlistCriteriaRequest;
import com.saas.recruitment.dto.response.ShortlistCriteriaResponse;
import com.saas.recruitment.service.ShortlistCriteriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/shortlist-criteria/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Shortlist criteria")
public class ShortlistCriteriaController {

    private final ShortlistCriteriaService shortlistCriteriaService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createShortlistCriteria(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody ShortlistCriteriaRequest request) {

        permissionEvaluator.addShortlistCriteriaPermission(tenantId);

        ShortlistCriteriaResponse response = shortlistCriteriaService
                .createShortlistCriteria(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all/{recruitment-id}")
    public ResponseEntity<?> getAllShortlistCriteria(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("recruitment-id") UUID recruitmentId) {

        permissionEvaluator.getAllShortlistCriteriaPermission(tenantId);

        List<ShortlistCriteriaResponse> responses = shortlistCriteriaService
                .getAllShortlistCriteria(tenantId, recruitmentId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{shortlist-criteria-id}")
    public ResponseEntity<?> getShortlistCriteriaById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("shortlist-criteria-id") UUID shortlistCriteriaId) {

        permissionEvaluator.getShortlistCriteriaByIdPermission(tenantId);

        ShortlistCriteriaResponse response = shortlistCriteriaService
                .getShortlistCriteriaById(tenantId, shortlistCriteriaId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{shortlist-criteria-id}")
    public ResponseEntity<?> updateShortlistCriteria(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("shortlist-criteria-id") UUID shortlistCriteriaId,
            @Valid @RequestBody ShortlistCriteriaRequest request) {

        permissionEvaluator.updateShortlistCriteriaPermission(tenantId);

        ShortlistCriteriaResponse response = shortlistCriteriaService
                .updateShortlistCriteria(tenantId, shortlistCriteriaId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{shortlist-criteria-id}")
    public ResponseEntity<?> deleteShortlistCriteria(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("shortlist-criteria-id") UUID shortlistCriteriaId) {

        permissionEvaluator.deleteShortlistCriteriaPermission(tenantId);

        shortlistCriteriaService.deleteShortlistCriteria(tenantId, shortlistCriteriaId);
        return ResponseEntity.ok("ShortlistCriteria deleted successfully");
    }
}