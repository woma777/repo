package com.saas.leave.controller;

import com.saas.leave.dto.request.LeaveScheduleRequest;
import com.saas.leave.dto.response.LeaveScheduleResponse;
import com.saas.leave.service.LeaveScheduleService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-schedules/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Schedule")
public class LeaveScheduleController {

    private final LeaveScheduleService leaveScheduleService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<LeaveScheduleResponse> createLeaveSchedule(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody LeaveScheduleRequest request) {

        permissionEvaluator.addLeaveSchedulePermission(tenantId);

        LeaveScheduleResponse response = leaveScheduleService.createLeaveSchedule(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveScheduleResponse>> getAllLeaveSchedules(@PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveSchedulesPermission(tenantId);

        List<LeaveScheduleResponse> responses = leaveScheduleService.getAllLeaveSchedules(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveScheduleResponse> getLeaveScheduleById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.getLeaveScheduleByIdPermission(tenantId);

        LeaveScheduleResponse response = leaveScheduleService.getLeaveScheduleById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveScheduleResponse> updateLeaveSchedule(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id,
            @RequestBody LeaveScheduleRequest request) {

        permissionEvaluator.updateLeaveSchedulePermission(tenantId);

        LeaveScheduleResponse response = leaveScheduleService.updateLeaveSchedule(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLeaveSchedule(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.deleteLeaveSchedulePermission(tenantId);

        leaveScheduleService.deleteLeaveSchedule(tenantId, id);
        return ResponseEntity.ok("deleted successfully !");
    }
}
