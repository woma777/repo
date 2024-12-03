package com.saas.training.controller;

import com.saas.training.dto.request.CheckedDocumentRequest;
import com.saas.training.dto.response.CheckedDocumentResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.CheckedDocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/documents/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Trainee Checked Document")
public class CheckedDocumentController {

    private final CheckedDocumentService checkedDocumentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCheckedDocument(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody CheckedDocumentRequest request) {

        permissionEvaluator.addPreServiceCheckedDocumentPermission(tenantId);

        CheckedDocumentResponse response = checkedDocumentService
                .addCheckedDocument(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCheckedDocuments(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllPreServiceCheckedDocumentsPermission(tenantId);

        List<CheckedDocumentResponse> responses = checkedDocumentService
                .getAllCheckedDocuments(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{document-id}")
    public ResponseEntity<?> getCheckedDocumentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("document-id") UUID documentId) {

        permissionEvaluator.getPreServiceCheckedDocumentByIdPermission(tenantId);

        CheckedDocumentResponse response = checkedDocumentService
                .getCheckedDocumentById(tenantId, documentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/trainee-documents/{trainee-id}")
    public ResponseEntity<?> getCheckedDocumentsByTraineeId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId) {

        permissionEvaluator.getPreServiceCheckedDocumentsByTraineeIdPermission(tenantId);

        List<CheckedDocumentResponse> responses = checkedDocumentService
                .getCheckedDocumentByTraineeId(tenantId, traineeId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/update/{document-id}")
    public ResponseEntity<?> updateCheckedDocument(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("document-id") UUID documentId,
            @Valid @RequestBody CheckedDocumentRequest request) {

        permissionEvaluator.updatePreServiceCheckedDocumentPermission(tenantId);

        CheckedDocumentResponse response = checkedDocumentService
                .updateCheckedDocument(tenantId, documentId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove-trainee-document/{trainee-id}/{document-id}")
    public ResponseEntity<?> removeCheckedDocumentFromTrainee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @PathVariable("document-id") UUID documentId) {

        permissionEvaluator.removeTraineeCheckedDocumentPermission(tenantId);

        checkedDocumentService.removeCheckedDocumentFromTrainee(tenantId, traineeId, documentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Document removed successfully!");
    }

    @DeleteMapping("/delete/{document-id}")
    public ResponseEntity<?> deleteCheckedDocument(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("document-id") UUID documentId) {

        permissionEvaluator.deletePreServiceCheckedDocumentPermission(tenantId);

        checkedDocumentService.deleteCheckedDocument(tenantId, documentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Document deleted successfully!");
    }
}