package com.saas.promotion.controller;

import com.saas.promotion.utility.PermissionEvaluator;
import com.saas.promotion.dto.request.CriteriaNameRequest;
import com.saas.promotion.dto.response.CriteriaNameResponse;
import com.saas.promotion.service.CriteriaNameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/promotion/criteria-names/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Promotion Criteria Name")
public class CriteriaNameController {

    private final CriteriaNameService criteriaNameService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCriteriaName(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CriteriaNameRequest request) {

        permissionEvaluator.addCriteriaNamePermission(tenantId);

        CriteriaNameResponse response =  criteriaNameService
                .addCriteriaName(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sub-criteria{parentId}/add")
    public ResponseEntity<?> addSubCriteriaName(
            @PathVariable UUID tenantId,
            @PathVariable UUID parentId,
            @RequestBody CriteriaNameRequest request) {

        permissionEvaluator.addCriteriaNamePermission(tenantId);

        CriteriaNameResponse response =  criteriaNameService
                .addSubCriteriaName(tenantId, parentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCriteriaNames(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllCriteriaNamesPermission(tenantId);

        List<CriteriaNameResponse> responses = criteriaNameService
                .getAllCriteriaNames(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/sub-criteria{parentId}/get-all")
    public ResponseEntity<?> getAllSubCriteriaNames(
            @PathVariable UUID tenantId,
            @PathVariable UUID parentId) {

        permissionEvaluator.getAllCriteriaNamesPermission(tenantId);

        List<CriteriaNameResponse> responses = criteriaNameService
                .getAllSubCriteriaNames(tenantId, parentId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("get/{criteriaNameId}")
    public ResponseEntity<?> getCriteriaNameById(
            @PathVariable UUID tenantId,
            @PathVariable UUID criteriaNameId) {

        permissionEvaluator.getCriteriaNameByIdPermission(tenantId);

        CriteriaNameResponse response = criteriaNameService
                .getCriteriaNameById(tenantId, criteriaNameId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("update/{criteriaNameId}")
    public ResponseEntity<?> updateCriteriaName(
            @PathVariable UUID tenantId,
            @PathVariable UUID criteriaNameId,
            @Valid @RequestBody CriteriaNameRequest request) {

        permissionEvaluator.updateCriteriaNamePermission(tenantId);

        CriteriaNameResponse response = criteriaNameService
                .updateCriteriaName(tenantId, criteriaNameId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("delete/{criteriaNameId}")
    public ResponseEntity<?> deleteCriteriaName(
            @PathVariable UUID tenantId,
            @PathVariable UUID criteriaNameId) {

        permissionEvaluator.deleteCriteriaNamePermission(tenantId);

        criteriaNameService.deleteCriteriaName(tenantId, criteriaNameId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Promotion criteria name deleted successfully!");
    }
}
