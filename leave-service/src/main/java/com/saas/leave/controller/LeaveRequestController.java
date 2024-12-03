package com.saas.leave.controller;

import com.saas.leave.dto.request.DepartmentApproveRequest;
import com.saas.leave.dto.request.HrDecision;
import com.saas.leave.dto.request.LeaveRequestRequest;
import com.saas.leave.dto.response.LeaveRequestResponse;
import com.saas.leave.enums.Status;
import com.saas.leave.service.LeaveRequestService;
import com.saas.leave.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leave-requests/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Leave Request")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@PathVariable("tenantId") UUID tenantId, @RequestBody LeaveRequestRequest leaveRequestRequest) {

        permissionEvaluator.addLeaveRequestPermission(tenantId);

        LeaveRequestResponse createdLeaveRequest = leaveRequestService.createLeaveRequest(tenantId, leaveRequestRequest);
        return new ResponseEntity<>(createdLeaveRequest, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<LeaveRequestResponse>> getAllLeaveRequests(@PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllLeaveRequestsPermission(tenantId);

        List<LeaveRequestResponse> leaveRequests = leaveRequestService.getAllLeaveRequests(tenantId);
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveRequestResponse> getLeaveRequestById(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.getLeaveRequestByIdPermission(tenantId);

        LeaveRequestResponse leaveRequest = leaveRequestService.getLeaveRequestById(tenantId, id);
        return ResponseEntity.ok(leaveRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LeaveRequestResponse> updateLeaveRequest(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id, @RequestBody LeaveRequestRequest leaveRequestRequest) {

        permissionEvaluator.updateLeaveRequestPermission(tenantId);

        LeaveRequestResponse updatedLeaveRequest = leaveRequestService.updateLeaveRequest(tenantId, id, leaveRequestRequest);
        return ResponseEntity.ok(updatedLeaveRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable("tenantId") UUID tenantId, @PathVariable UUID id) {

        permissionEvaluator.deleteLeaveRequestPermission(tenantId);

        leaveRequestService.deleteLeaveRequest(tenantId, id);
        return ResponseEntity.ok("Leave Request Deleted Successfully!");
    }
    @PostMapping("/calculate")
    public ResponseEntity<LocalDate> calculateReturnDate(@RequestBody LeaveRequestRequest leaveRequestRequest, @PathVariable String tenantId) {
        LocalDate returnDate = leaveRequestService.calculateReturnDate(leaveRequestRequest);
        return ResponseEntity.ok(returnDate);
    }

    @PutMapping("/department-approve/{requestId}")
    public ResponseEntity<?> departmentApprove(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID requestId,
            @RequestBody DepartmentApproveRequest request) {

        permissionEvaluator.departmentApproveLeaveRequestPermission(tenantId);

        LeaveRequestResponse response = leaveRequestService
                .approveLeaveRequest(tenantId, requestId,request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/hr-approve/{requestId}")
    public ResponseEntity<?> HrApprove(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID requestId,
            @RequestBody HrDecision hrDecision) {

        permissionEvaluator.hrApproveLeaveRequestPermission(tenantId);

        LeaveRequestResponse response = leaveRequestService
                .hRDecision(tenantId, requestId, hrDecision);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/status")
    public ResponseEntity<?> getLeaveRequestsByStatus(
            @PathVariable("tenantId") UUID tenantId,
            @RequestParam Status decision) {

        permissionEvaluator.getLeaveRequestsByStatusPermission(tenantId);

        List<LeaveRequestResponse> responses = leaveRequestService
                .getLeaveRequestsByStatus(tenantId, decision);
        return ResponseEntity.ok(responses);
    }
}
