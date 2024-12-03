package com.saas.employee.controller;

import com.saas.employee.utility.PermissionEvaluator;
import com.saas.employee.dto.request.LanguageRequest;
import com.saas.employee.dto.response.LanguageResponse;
import com.saas.employee.service.LanguageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/languages/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Language")
public class LanguageController {

    private final LanguageService languageService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @Valid @RequestBody LanguageRequest languageRequest) {

        permissionEvaluator.addLanguagePermission(tenantId, employeeId);

        LanguageResponse language = languageService
                .addLanguage(tenantId, employeeId, languageRequest);
        return new ResponseEntity<>(language, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllLanguages(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllLanguagesPermission(tenantId, employeeId);

        List<LanguageResponse> languages = languageService
                .getAllLanguages(tenantId, employeeId);
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/get/employee-languages")
    public ResponseEntity<?> getEmployeeLanguages(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getLanguagesByEmployeeIdPermission(tenantId);

        List<LanguageResponse> languages = languageService
                .getEmployeeLanguages(tenantId, employeeId);
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/{employee-id}/get/{language-id}")
    public ResponseEntity<?> getLanguageById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.getLanguageByIdPermission(tenantId, employeeId);

        LanguageResponse language = languageService
                .getLanguageById(tenantId, employeeId, languageId);
        return ResponseEntity.ok(language);
    }

    @PutMapping("/{employee-id}/update/{language-id}")
    public ResponseEntity<?> updateLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("language-id") UUID languageId,
            @Valid @RequestBody LanguageRequest languageRequest) {

        permissionEvaluator.updateLanguagePermission(tenantId, employeeId);

        LanguageResponse language = languageService
                .updateLanguage(tenantId, employeeId, languageId, languageRequest);
        return ResponseEntity.ok(language);
    }

    @DeleteMapping("/{employee-id}/delete/{language-id}")
    public ResponseEntity<?> deleteLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.deleteLanguagePermission(tenantId, employeeId);

        languageService.deleteLanguage(tenantId, employeeId, languageId);
        return ResponseEntity.ok("Language deleted successfully");
    }
}