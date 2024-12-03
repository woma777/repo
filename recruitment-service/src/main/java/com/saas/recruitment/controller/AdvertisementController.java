package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.AdvertisementRequest;
import com.saas.recruitment.dto.response.AdvertisementResponse;
import com.saas.recruitment.service.AdvertisementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/advertisements/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createAdvertisement(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody AdvertisementRequest request) {

        permissionEvaluator.addAdvertisementPermission(tenantId);

        AdvertisementResponse response = advertisementService
                .createAdvertisement(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAdvertisements(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllAdvertisementsPermission(tenantId);

        List<AdvertisementResponse> responses = advertisementService
                .getAllAdvertisements(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{advertisement-id}")
    public ResponseEntity<?> getAdvertisementById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId) {

        permissionEvaluator.getAdvertisementByIdPermission(tenantId);

        AdvertisementResponse response = advertisementService
                .getAdvertisementById(tenantId, advertisementId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/recruitment")
    public ResponseEntity<?> getAdvertisementByVacancyNumber(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("vacancy-number") String vacancyNumber) {

        permissionEvaluator.getAdvertisementByVacancyNumberPermission(tenantId);

        AdvertisementResponse response = advertisementService
                .getAdvertisementByVacancyNumber(tenantId, vacancyNumber);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{advertisement-id}")
    public ResponseEntity<?> updateAdvertisement(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId,
            @Valid @RequestBody AdvertisementRequest request) {

        permissionEvaluator.updateAdvertisementPermission(tenantId);

        AdvertisementResponse response = advertisementService
                .updateAdvertisement(tenantId, advertisementId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{advertisement-id}/remove-advertisement-media/{media-type-id}")
    public ResponseEntity<?> removeMediaTypeFromAdvertisement(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId,
            @PathVariable("media-type-id") UUID mediaTypeId) {

        permissionEvaluator.removeAdvertisementMediaTypePermission(tenantId);

        advertisementService.removeMediaTypeFromAdvertisement(tenantId, advertisementId, mediaTypeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Media type removed successfully!");
    }

    @DeleteMapping("/delete/{advertisement-id}")
    public ResponseEntity<?> deleteAdvertisement(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId) {

        permissionEvaluator.deleteAdvertisementPermission(tenantId);

        advertisementService.deleteAdvertisement(tenantId, advertisementId);
        return ResponseEntity.ok("Advertisement deleted successfully!");
    }
}