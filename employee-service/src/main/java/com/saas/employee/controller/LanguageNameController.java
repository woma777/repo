package com.saas.employee.controller;

import com.saas.employee.utility.PermissionEvaluator;
import com.saas.employee.dto.request.LanguageNameRequest;
import com.saas.employee.dto.response.LanguageNameResponse;
import com.saas.employee.service.LanguageNameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/language-names/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Language Name")
public class LanguageNameController {

    private final LanguageNameService languageNameService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addLanguageName(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody LanguageNameRequest request) {

        permissionEvaluator.addLanguageNamePermission(tenantId);

        LanguageNameResponse response = languageNameService
                .addLanguageName(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLanguageNames(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllLanguageNamesPermission(tenantId);

        List<LanguageNameResponse> responses = languageNameService
                .getAllLanguageNames(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{language-id}")
    public ResponseEntity<?> getLanguageNameById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.getLanguageNameByIdPermission(tenantId);

        LanguageNameResponse response = languageNameService
                .getLanguageNameById(tenantId, languageId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{language-id}")
    public ResponseEntity<?> updateLanguageName(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("language-id") UUID languageId,
            @Valid @RequestBody LanguageNameRequest request) {

        permissionEvaluator.updateLanguageNamePermission(tenantId);

        LanguageNameResponse response = languageNameService
                .updateLanguageName(tenantId, languageId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{language-id}")
    public ResponseEntity<?> deleteLanguageName(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.deleteLanguageNamePermission(tenantId);

        languageNameService.deleteLanguageName(tenantId, languageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Language name deleted successfully!");
    }
}