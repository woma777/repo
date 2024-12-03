package com.saas.leave.controller;

import com.saas.leave.dto.request.LeaveSettingRequest;
import com.saas.leave.dto.response.LeaveSettingResponse;
import com.saas.leave.service.LeaveSettingService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-settings/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Setting")
public class LeaveSettingController {

    private final LeaveSettingService leaveSettingService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<LeaveSettingResponse> createLeaveSetting(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveSettingRequest leaveSettingRequest) {

        permissionEvaluator.addLeaveSettingPermission(tenantId);

        LeaveSettingResponse response = leaveSettingService.createLeaveSetting(tenantId, leaveSettingRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveSettingResponse>> getAllLeaveSettings(@PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveSettingsPermission(tenantId);

        List<LeaveSettingResponse> responses = leaveSettingService.getAllLeaveSettings(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveSettingResponse> getLeaveSettingById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getLeaveSettingByIdPermission(tenantId);

        LeaveSettingResponse response = leaveSettingService.getLeaveSettingById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveSettingResponse> updateLeaveSetting(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody LeaveSettingRequest leaveSettingRequest) {

        permissionEvaluator.updateLeaveSettingPermission(tenantId);

        LeaveSettingResponse response = leaveSettingService.updateLeaveSetting(tenantId, id, leaveSettingRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLeaveSetting(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteLeaveSettingPermission(tenantId);

        leaveSettingService.deleteLeaveSetting(tenantId, id);
        return ResponseEntity.ok("Leave Setting is Deleted Successfully!");
    }
}
