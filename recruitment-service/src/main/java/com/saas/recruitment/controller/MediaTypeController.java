package com.saas.recruitment.controller;

import com.saas.recruitment.utility.PermissionEvaluator;
import com.saas.recruitment.dto.request.MediaTypeRequest;
import com.saas.recruitment.dto.response.MediaTypeResponse;
import com.saas.recruitment.service.MediaTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruitment/media-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Media Type")
public class MediaTypeController {

    private final MediaTypeService mediaTypeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addMediaType(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody MediaTypeRequest request) {

        permissionEvaluator.addMediaTypePermission(tenantId);

        MediaTypeResponse response = mediaTypeService
                .addMediaType(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllMediaTypes(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllMediaTypesPermission(tenantId);

        List<MediaTypeResponse> responses = mediaTypeService
                .getAllMediaTypes(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{media-type-id}")
    public ResponseEntity<?> getMediaTypeById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("media-type-id") UUID mediaTypeId) {

        permissionEvaluator.getMediaTypeByIdPermission(tenantId);

        MediaTypeResponse response = mediaTypeService
                .getMediaTypeById(tenantId, mediaTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/advertisement-media/{advertisement-id}")
    public ResponseEntity<?> getMediaTypeByAdvertisementId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("advertisement-id") UUID advertisementId) {

        permissionEvaluator.getMediaTypesByAdvertisementIdPermission(tenantId);

        List<MediaTypeResponse> responses = mediaTypeService
                .getAllMediaTypesByAdvertisementId(tenantId, advertisementId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/update/{media-type-id}")
    public ResponseEntity<?> updateMediaType(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("media-type-id") UUID mediaTypeId,
            @Valid @RequestBody MediaTypeRequest request) {

        permissionEvaluator.updateMediaTypePermission(tenantId);

        MediaTypeResponse response = mediaTypeService
                .updateMediaType(tenantId, mediaTypeId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{media-type-id}")
    public ResponseEntity<?> deleteMediaType(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("media-type-id") UUID mediaTypeId) {

        permissionEvaluator.deleteMediaTypePermission(tenantId);

        mediaTypeService.deleteMediaType(tenantId, mediaTypeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Media type deleted successfully!");
    }
}