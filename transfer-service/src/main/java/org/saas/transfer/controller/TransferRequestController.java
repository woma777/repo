package org.saas.transfer.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.saas.transfer.dto.request.ApproveRequestDto;
import org.saas.transfer.dto.request.RequestDto;
import org.saas.transfer.dto.response.ResponseDto;
import org.saas.transfer.service.TransferRequestService;
import org.saas.transfer.utility.PermissionEvaluator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transfer-requests/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Transfer Request Controller")
public class TransferRequestController {

    private final TransferRequestService transferRequestService;
    private final PermissionEvaluator permissionEvaluator;

    // Endpoint to create a transfer request
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> createTransferRequest(
            @PathVariable UUID tenantId,
            @Valid @RequestBody RequestDto requestDto) {
        permissionEvaluator.addTransferRequestPermission(tenantId);
        ResponseDto response = transferRequestService.CreateTransferRequest(tenantId, requestDto);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get all transfer requests for a specific tenant
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseDto>> getAllTransferRequests(
            @PathVariable UUID tenantId) {
        permissionEvaluator.getAllTransferRequestPermission(tenantId);
        List<ResponseDto> responseList = transferRequestService.getAllTransferRequests(tenantId);
        return ResponseEntity.ok(responseList);
    }

    // Endpoint to get a specific transfer request by its ID
    @GetMapping("/get-by/{transferRequestId}")
    public ResponseEntity<ResponseDto> getTransferRequestById(
            @PathVariable UUID tenantId,
            @PathVariable UUID transferRequestId) {
        permissionEvaluator.getTransferRequestByIdPermission(tenantId);
        ResponseDto response = transferRequestService.getTransferRequestById(tenantId, transferRequestId);
        return ResponseEntity.ok(response);
    }

    // Endpoint to update a specific transfer request by its ID
    @PutMapping("/update/{transferRequestId}")
    public ResponseEntity<ResponseDto> updateTransferRequestById(
            @PathVariable UUID tenantId,
            @PathVariable UUID transferRequestId,
            @Valid @RequestBody RequestDto requestDto) {
        permissionEvaluator.updateTransferRequestPermission(tenantId);
        ResponseDto response = transferRequestService.updateTransferRequestById(tenantId, transferRequestId, requestDto);
        return ResponseEntity.ok(response);
    }

    // Endpoint to delete a specific transfer request by its ID
    @DeleteMapping("/delete/{transferRequestId}")
    public ResponseEntity<Void> deleteTransferRequest(
            @PathVariable UUID tenantId,
            @PathVariable UUID transferRequestId) {
        permissionEvaluator.getTransferRequestDeletePermission(tenantId);
        transferRequestService.deleteTransferRequest(tenantId, transferRequestId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to approve a specific transfer request by its ID
    @PutMapping("/transfer-approve/{transferRequestId}")
    public ResponseEntity<ResponseDto> approveTransferRequest(
            @PathVariable UUID tenantId,
            @PathVariable UUID transferRequestId,
            @Valid @RequestBody ApproveRequestDto approveRequestDto) {
        permissionEvaluator.getTransferRequestApprovePermission(tenantId);
        ResponseDto response = transferRequestService.ApproveTransferRequest(tenantId, transferRequestId, approveRequestDto);
        return ResponseEntity.ok(response);
    }
}
