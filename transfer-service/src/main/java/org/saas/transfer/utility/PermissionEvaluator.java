package org.saas.transfer.utility;

import lombok.RequiredArgsConstructor;
import org.saas.transfer.enums.TransferServiceResourceName;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;


    private void checkPermission(UUID tenantId, TransferServiceResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }

    public void addTransferRequestPermission(UUID tenantId) {
        checkPermission(tenantId, TransferServiceResourceName.CREAT_TRANSFER);
        
    }


    public void getAllTransferRequestPermission(UUID tenantId) {
        checkPermission(tenantId, TransferServiceResourceName.GET_ALL_TRANSFER_REQUEST);
    }

    public void getTransferRequestByIdPermission(UUID tenantId) {
        checkPermission(tenantId,TransferServiceResourceName.GET_TRANSFER_REQUEST_BY_ID);
        
    }

    public void updateTransferRequestPermission(UUID tenantId) {
        checkPermission(tenantId, TransferServiceResourceName.UPDATE_TRANSFER_REQUEST);
    }

    public void getTransferRequestDeletePermission(UUID tenantId) {
        checkPermission(tenantId, TransferServiceResourceName.DELETE_TRANSFER_REQUEST);
    }

    public void getTransferRequestApprovePermission(UUID tenantId) {
        checkPermission(tenantId, TransferServiceResourceName.APPROVE_TRANSFER_REQUEST);
    }

    public void addDirectAssignment(UUID tenantId) {
        checkPermission(tenantId,TransferServiceResourceName.CREAT_ASSIGNMENT);
    }

    public void getAllDirectAssignment(UUID tenantId) {
        checkPermission(tenantId,TransferServiceResourceName.GET_ALL_ASSIGNMENT);
    }

    public void getDirectAssignmentById(UUID tenantId) {
        checkPermission(tenantId,TransferServiceResourceName.GET_ASSIGNMENT_BY_ID);
    }

    public void updateDirectAssignment(UUID tenantId) {
        checkPermission(tenantId,TransferServiceResourceName.UPDATE_ASSIGNMENT);
    }

    public void deleteDirectAssignment(UUID tenantId) {
        checkPermission(tenantId,TransferServiceResourceName.DELETE_ASSIGNMENT);
    }
}