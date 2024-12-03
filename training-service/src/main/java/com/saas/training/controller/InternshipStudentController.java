package com.saas.training.controller;

import com.saas.training.dto.request.InternshipPlacementRequest;
import com.saas.training.dto.request.InternshipStudentRequest;
import com.saas.training.dto.response.InternshipStudentResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.InternshipStudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/internship-students/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Internship Student")
public class InternshipStudentController {

    private final InternshipStudentService internshipStudentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addInternshipStudent(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody InternshipStudentRequest request) {

        permissionEvaluator.addInternshipStudentPermission(tenantId);

        InternshipStudentResponse response = internshipStudentService
                .addInternshipStudent(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInternshipStudents(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllInternshipStudentsPermission(tenantId);

        List<InternshipStudentResponse> responses = internshipStudentService
                .getAllInternshipStudents(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{intern-id}")
    public ResponseEntity<?> getInternshipStudentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("intern-id") UUID internId) {

        permissionEvaluator.getInternshipStudentByIdPermission(tenantId);

        InternshipStudentResponse response = internshipStudentService
                .getInternshipStudentById(tenantId, internId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all/{budget-year-id}")
    public ResponseEntity<?> getInternshipStudentsByYearAndSemester(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("budget-year-id") UUID yearId,
            @RequestParam("Semester") String semester) {

        permissionEvaluator.getInternshipStudentsByBudgetYearIdPermission(tenantId);

        List<InternshipStudentResponse> responses = internshipStudentService
                .getInternshipStudentsByYearAndSemester(tenantId, yearId, semester);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/update/{intern-id}")
    public ResponseEntity<?> updateInternshipStudent(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("intern-id") UUID internId,
            @Valid @RequestBody InternshipStudentRequest request) {

        permissionEvaluator.updateInternshipStudentPermission(tenantId);

        InternshipStudentResponse response = internshipStudentService
                .updateInternshipStudent(tenantId, internId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/assign-department/{intern-id}")
    public ResponseEntity<?> assignInternToPlacementDepartment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("intern-id") UUID internId,
            @Valid @RequestBody InternshipPlacementRequest request) {

        permissionEvaluator.assignDepartmentToInternshipStudentPermission(tenantId);

        InternshipStudentResponse response = internshipStudentService
                .assignInternToPlacementDepartment(tenantId, internId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/assign-status/{intern-id}")
    public ResponseEntity<?> assignInternshipStatus(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("intern-id") UUID internId,
            @RequestParam("status") String internshipStatus) {

        permissionEvaluator.assignStatusToInternshipStudentPermission(tenantId);

        InternshipStudentResponse response = internshipStudentService
                .assignInternshipStatus(tenantId, internId, internshipStatus);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{intern-id}")
    public ResponseEntity<?> deleteInternshipStudent(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("intern-id") UUID internId) {

        permissionEvaluator.deleteInternshipStudentPermission(tenantId);

        internshipStudentService.deleteInternshipStudent(tenantId, internId);
        return ResponseEntity.ok("Internship student deleted successfully");
    }
}