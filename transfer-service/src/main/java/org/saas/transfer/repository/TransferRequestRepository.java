package org.saas.transfer.repository;

import org.saas.transfer.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TransferRequestRepository extends JpaRepository<TransferRequest, UUID> {

    @Query("SELECT tr FROM TransferRequest tr WHERE tr.tenantId = :tenantId AND tr.id = :transferRequestId")
    TransferRequest getTransferRequestById(@Param("tenantId") UUID tenantId, @Param("transferRequestId") UUID transferRequestId);
}
