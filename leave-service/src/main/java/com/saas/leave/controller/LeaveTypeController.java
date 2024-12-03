package com.saas.leave.controller;

import com.saas.leave.dto.request.LeaveTypeRequest;
import com.saas.leave.dto.response.LeaveTypeResponse;
import com.saas.leave.service.LeaveTypeService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-types/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Type")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<LeaveTypeResponse> createLeaveType(@PathVariable("tenantId") UUID tenantId, @RequestBody LeaveTypeRequest leaveTypeRequest) {

        permissionEvaluator.addLeaveTypePermission(tenantId);

        LeaveTypeResponse leaveTypeResponse = leaveTypeService.createLeaveType(tenantId, leaveTypeRequest);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveTypeResponse>> getAllLeaveTypes(@PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveTypesPermission(tenantId);

        List<LeaveTypeResponse> leaveTypeResponses = leaveTypeService.getAllLeaveTypes(tenantId);
        return ResponseEntity.ok(leaveTypeResponses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveTypeResponse> getLeaveTypeById(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.getLeaveTypeByIdPermission(tenantId);

        LeaveTypeResponse leaveTypeResponse = leaveTypeService.getLeaveTypeById(tenantId, id);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveTypeResponse> updateLeaveType(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id, @RequestBody LeaveTypeRequest leaveTypeRequest) {

        permissionEvaluator.updateLeaveTypePermission(tenantId);

        LeaveTypeResponse leaveTypeResponse = leaveTypeService.updateLeaveType(tenantId, id, leaveTypeRequest);
        return ResponseEntity.ok(leaveTypeResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLeaveType(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.deleteLeaveTypePermission(tenantId);

        leaveTypeService.deleteLeaveType(tenantId, id);
        return ResponseEntity.ok("Holiday remove successfully !");
    }
}
