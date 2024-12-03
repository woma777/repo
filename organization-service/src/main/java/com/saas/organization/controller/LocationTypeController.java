package com.saas.organization.controller;

import com.saas.organization.utility.PermissionEvaluator;
import com.saas.organization.dto.requestDto.LocationTypeRequest;
import com.saas.organization.dto.responseDto.LocationTypeResponse;
import com.saas.organization.service.LocationTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organization/location-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Locations Type")
public class LocationTypeController {

    private final LocationTypeService locationTypeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-location-type")
    public ResponseEntity<?> createLocationType(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody LocationTypeRequest locationTypeRequest) {

        permissionEvaluator.addLocationTypePermission(tenantId);

        LocationTypeResponse location = locationTypeService
                .createLocationType(tenantId, locationTypeRequest);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLocationTypes(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllLocationTypesPermission(tenantId);

        List<LocationTypeResponse> locationTypes = locationTypeService
                .getAllLocationTypes(tenantId);
        return ResponseEntity.ok(locationTypes);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLocationTypeById(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getLocationTypeByIdPermission(tenantId);

        LocationTypeResponse location = locationTypeService.getLocationTypeById(id, tenantId);
        return ResponseEntity.ok(location);
    }

    @PutMapping("/update-locationType/{id}")
    public ResponseEntity<?> updateLocationType(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody LocationTypeRequest locationTypeRequest) {

        permissionEvaluator.updateLocationTypePermission(tenantId);

        LocationTypeResponse location = locationTypeService
                .updateLocationType(id, tenantId, locationTypeRequest);
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/delete-locationType/{id}")
    public ResponseEntity<?> deleteLocationType(
            @PathVariable UUID id,
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.deleteLocationTypePermission(tenantId);

        locationTypeService.deleteLocationType(id, tenantId);
        return ResponseEntity.ok("LocationType deleted successfully!");
    }
}