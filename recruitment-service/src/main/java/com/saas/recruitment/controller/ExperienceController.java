package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.ExperienceRequest;
import com.saas.recruitment.dto.response.ExperienceResponse;
import com.saas.recruitment.service.ExperienceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/applicant-experiences/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Experience")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addExperience(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @Valid @RequestPart("experience") ExperienceRequest experienceRequest,
            @RequestPart("document") MultipartFile file) throws IOException {

        permissionEvaluator.addExperiencePermission(tenantId);

        ExperienceResponse experience = experienceService
                .addExperience(tenantId, applicantId, experienceRequest, file);
        return new ResponseEntity<>(experience, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllExperiences(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId) {

        permissionEvaluator.getAllExperiencesPermission(tenantId);

        List<ExperienceResponse> experiences = experienceService
                .getAllExperiences(tenantId, applicantId);
        return ResponseEntity.ok(experiences);
    }

    @GetMapping("/get/{experience-id}")
    public ResponseEntity<?> getExperienceById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("experience-id") UUID experienceId) {

        permissionEvaluator.getExperienceByIdPermission(tenantId);

        ExperienceResponse experience = experienceService
                .getExperienceById(tenantId, applicantId, experienceId);
        return ResponseEntity.ok(experience);
    }

    @GetMapping(value = "/download-document/{experience-id}")
    public ResponseEntity<?> getExperienceDocumentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("experience-id") UUID experienceId) {

        permissionEvaluator.downloadExperienceDocumentPermission(tenantId);

        ExperienceResponse experience = experienceService
                .getExperienceById(tenantId, applicantId, experienceId);
        if (experience == null || experience.getFileType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Applicant experience document not found");
        }
        MediaType mediaType = MediaType.valueOf(experience.getFileType());
        byte[] documentBytes = experienceService
                .getExperienceDocumentById(tenantId, applicantId, experienceId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
    }

    @PutMapping("/update/{experience-id}")
    public ResponseEntity<?> updateExperience(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("experience-id") UUID experienceId,
            @Valid @RequestPart("experience") ExperienceRequest request,
            @RequestPart(value = "document", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateExperiencePermission(tenantId);

        ExperienceResponse experience = experienceService
                .updateExperience(tenantId, applicantId, experienceId, request, file);
        return ResponseEntity.ok(experience);
    }

    @DeleteMapping("/delete/{experience-id}")
    public ResponseEntity<?> deleteExperience(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("applicant-id") UUID applicantId,
            @PathVariable("experience-id") UUID experienceId) {

        permissionEvaluator.deleteExperiencePermission(tenantId);

        experienceService.deleteExperience(tenantId, applicantId, experienceId);
        return ResponseEntity.ok("Experience deleted successfully");
    }
}