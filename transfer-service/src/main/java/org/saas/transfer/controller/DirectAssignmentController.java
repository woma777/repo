package org.saas.transfer.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.saas.transfer.dto.request.AssignmentRequestDto;
import org.saas.transfer.dto.response.AssignmentResponseDto;
import org.saas.transfer.service.DirectAssignmentService;
import org.saas.transfer.utility.PermissionEvaluator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/assignments")
@RequiredArgsConstructor
@Tag(name = "Direct Assignment Controller")
public class DirectAssignmentController {

    private final DirectAssignmentService directAssignmentService;
    private final PermissionEvaluator permissionEvaluator;

    // Endpoint to create a new assignment
    @PostMapping("/add")
    public ResponseEntity<AssignmentResponseDto> createAssignment(
            @PathVariable UUID tenantId,
            @Valid @RequestBody AssignmentRequestDto assignmentRequestDto) {
        permissionEvaluator.addDirectAssignment(tenantId);

        AssignmentResponseDto responseDto = directAssignmentService.createAssignment(tenantId, assignmentRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Endpoint to get all assignments for a tenant
    @GetMapping("/get-direct-assignments/")
    public ResponseEntity<List<AssignmentResponseDto>> getAllAssignments(@PathVariable UUID tenantId) {
        permissionEvaluator.getAllDirectAssignment(tenantId);
        List<AssignmentResponseDto> assignments = directAssignmentService.getAllAssignments(tenantId);
        return ResponseEntity.ok(assignments);
    }

    // Endpoint to get a specific assignment by its ID
    @GetMapping("/get-by/{directAssignmentId}")
    public ResponseEntity<AssignmentResponseDto> getAssignmentById(
            @PathVariable UUID tenantId,
            @PathVariable UUID directAssignmentId) {
        permissionEvaluator.getDirectAssignmentById(tenantId);
        AssignmentResponseDto responseDto = directAssignmentService.getAssignmentById(tenantId, directAssignmentId);
        return ResponseEntity.ok(responseDto);
    }

    // Endpoint to update an existing assignment
    @PutMapping("/update-by/{directAssignmentId}")
    public ResponseEntity<AssignmentResponseDto> updateAssignment(
            @PathVariable UUID tenantId,
            @PathVariable UUID directAssignmentId,
            @Valid @RequestBody AssignmentRequestDto assignmentRequestDto) {
        permissionEvaluator.updateDirectAssignment(tenantId);
        AssignmentResponseDto updatedAssignment = directAssignmentService.updateDirectAssignment(
                tenantId, directAssignmentId, assignmentRequestDto);
        return ResponseEntity.ok(updatedAssignment);
    }

    // Endpoint to delete an assignment
    @DeleteMapping("/delete/{directAssignmentId}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable UUID tenantId,
            @PathVariable UUID directAssignmentId) {
        permissionEvaluator.deleteDirectAssignment(tenantId);
        directAssignmentService.deleteDirectAssignment(tenantId, directAssignmentId);
        return ResponseEntity.noContent().build();
    }
}
