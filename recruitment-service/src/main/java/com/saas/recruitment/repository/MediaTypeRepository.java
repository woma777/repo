package com.saas.recruitment.repository;

import com.saas.recruitment.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaTypeRepository extends JpaRepository<MediaType, UUID> {

    boolean existsByMediaTypeNameAndTenantId(String mediaTypeName, UUID tenantId);
    boolean existsByTenantIdAndMediaTypeNameAndIdNot(UUID tenantId, String mediaTypeName, UUID id);
    List<MediaType> findMediaTypeByAdvertisementsId(UUID advertisementId);
}
