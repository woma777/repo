package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.LanguageRequest;
import com.saas.recruitment.dto.response.LanguageResponse;
import com.saas.recruitment.service.LanguageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/applicant-languages/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Language")
public class LanguageController {

    private final LanguageService languageService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @Valid @RequestBody LanguageRequest languageRequest) {

        permissionEvaluator.addLanguagePermission(tenantId);

        LanguageResponse language = languageService
                .addLanguage(tenantId, applicantId, languageRequest);
        return new ResponseEntity<>(language, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLanguages(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId) {

        permissionEvaluator.getAllLanguagesPermission(tenantId);

        List<LanguageResponse> languages = languageService
                .getAllLanguages(tenantId, applicantId);
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/get/{language-id}")
    public ResponseEntity<?> getLanguageById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.getLanguageByIdPermission(tenantId);

        LanguageResponse language = languageService
                .getLanguageById(tenantId, applicantId, languageId);
        return ResponseEntity.ok(language);
    }

    @PutMapping("/update/{language-id}")
    public ResponseEntity<?> updateLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("language-id") UUID languageId,
            @Valid @RequestBody LanguageRequest languageRequest) {

        permissionEvaluator.updateLanguagePermission(tenantId);

        LanguageResponse language = languageService
                .updateLanguage(tenantId, applicantId, languageId, languageRequest);
        return ResponseEntity.ok(language);
    }

    @DeleteMapping("/delete/{language-id}")
    public ResponseEntity<?> deleteLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.deleteLanguagePermission(tenantId);

        languageService.deleteLanguage(tenantId, applicantId, languageId);
        return ResponseEntity.ok("Language deleted successfully");
    }
}