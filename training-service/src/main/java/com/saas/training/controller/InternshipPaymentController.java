package com.saas.training.controller;

import com.saas.training.dto.request.InternshipPaymentRequest;
import com.saas.training.dto.response.InternshipPaymentResponse;
import com.saas.training.utility.PermissionEvaluator;
import com.saas.training.service.InternshipPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training/internship-payments/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Internship Payment")
public class InternshipPaymentController {

    private final InternshipPaymentService internshipPaymentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addInternshipPayment(
            @PathVariable("tenant-id") UUID tenantId,
            @Valid @RequestBody InternshipPaymentRequest request) {

        permissionEvaluator.addInternshipPaymentPermission(tenantId);

        InternshipPaymentResponse response = internshipPaymentService
                .createInternshipPayment(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInternshipPayments(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllInternshipPaymentsPermission(tenantId);

        List<InternshipPaymentResponse> responses = internshipPaymentService
                .getAllInternshipPayments(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{payment-id}")
    public ResponseEntity<?> getInternshipPaymentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("payment-id") UUID paymentId) {

        permissionEvaluator.getInternshipPaymentByIdPermission(tenantId);

        InternshipPaymentResponse response = internshipPaymentService
                .getInternshipPaymentById(tenantId, paymentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{payment-id}")
    public ResponseEntity<?> updateInternshipPayment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("payment-id") UUID paymentId,
            @Valid @RequestBody InternshipPaymentRequest request) {

        permissionEvaluator.updateInternshipPaymentPermission(tenantId);

        InternshipPaymentResponse response = internshipPaymentService
                .updateInternshipPayment(tenantId, paymentId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{payment-id}")
    public ResponseEntity<?> deleteInternshipPayment(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("payment-id") UUID paymentId) {

        permissionEvaluator.deleteInternshipPaymentPermission(tenantId);

        internshipPaymentService.deleteInternshipPayment(tenantId, paymentId);
        return ResponseEntity.ok("Internship payment deleted successfully!");
    }
}