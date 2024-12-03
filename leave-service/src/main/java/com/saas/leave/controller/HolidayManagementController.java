package com.saas.leave.controller;

import com.saas.leave.dto.request.HolidayManagementRequest;
import com.saas.leave.dto.response.HolidayManagementResponse;
import com.saas.leave.service.HolidayManagementService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/holiday-managements/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Holiday Management")
public class HolidayManagementController {

    private final HolidayManagementService holidayManagementService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<HolidayManagementResponse> createHolidayManagement(@PathVariable("tenantId") UUID tenantId,
                                                                             @RequestBody @Valid HolidayManagementRequest holidayManagementRequest) {

        permissionEvaluator.addHolidayManagementPermission(tenantId);

        HolidayManagementResponse createdHolidayManagement = holidayManagementService.createHolidayManagement(tenantId, holidayManagementRequest);
        return  ResponseEntity.ok(createdHolidayManagement);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<HolidayManagementResponse>> getAllHolidayManagements(@PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHolidayManagementsPermission(tenantId);

        List<HolidayManagementResponse> holidayManagements = holidayManagementService.getAllHolidayManagements(tenantId);
        return  ResponseEntity.ok(holidayManagements);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HolidayManagementResponse> getHolidayManagementById(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.getHolidayManagementByIdPermission(tenantId);

        HolidayManagementResponse holidayManagement = holidayManagementService.getHolidayManagementById(tenantId, id);
        return ResponseEntity.ok(holidayManagement );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HolidayManagementResponse> updateHolidayManagement(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id, @RequestBody @Valid HolidayManagementRequest holidayManagementRequest) {

        permissionEvaluator.updateHolidayManagementPermission(tenantId);

        HolidayManagementResponse updatedHolidayManagement = holidayManagementService.updateHolidayManagement(tenantId, id, holidayManagementRequest);
        return  ResponseEntity.ok(updatedHolidayManagement);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHolidayManagement(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.deleteHolidayManagementPermission(tenantId);

        holidayManagementService.deleteHolidayManagement(tenantId, id);
        return ResponseEntity.ok("Holiday Management deleted successfully!");
    }
}
