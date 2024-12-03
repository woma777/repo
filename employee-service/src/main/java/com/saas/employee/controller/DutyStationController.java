package com.saas.employee.controller;

import com.saas.employee.dto.request.DutyStationRequest;
import com.saas.employee.dto.response.DutyStationResponse;
import com.saas.employee.service.DutyStationService;
import com.saas.employee.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee/duty-stations/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Duty Station")
public class DutyStationController {

    private final DutyStationService dutyStationService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addDutyStation(
            @PathVariable UUID tenantId,
            @Valid @RequestBody DutyStationRequest request) {

        permissionEvaluator.addDutyStationPermission(tenantId);

        DutyStationResponse response = dutyStationService
                .addDutyStation(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDutyStations(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllDutyStationsPermission(tenantId);

        List<DutyStationResponse> responses = dutyStationService
                .getAllDutyStations(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{stationId}")
    public ResponseEntity<?> getDutyStationById(
            @PathVariable UUID tenantId,
            @PathVariable UUID stationId) {

        permissionEvaluator.getDutyStationByIdPermission(tenantId);

        DutyStationResponse response = dutyStationService
                .getDutyStationById(tenantId, stationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{stationId}")
    public ResponseEntity<?> updateDutyStation(
            @PathVariable UUID tenantId,
            @PathVariable UUID stationId,
            @Valid @RequestBody DutyStationRequest request) {

        permissionEvaluator.updateDutyStationPermission(tenantId);

        DutyStationResponse response = dutyStationService
                .updateDutyStation(tenantId, stationId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/delete/{stationId}")
    public ResponseEntity<?> deleteDutyStation(
            @PathVariable UUID tenantId,
            @PathVariable UUID stationId) {

        permissionEvaluator.deleteDutyStationPermission(tenantId);

        dutyStationService.deleteDutyStation(tenantId, stationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Duty station deleted successfully!");
    }
}
