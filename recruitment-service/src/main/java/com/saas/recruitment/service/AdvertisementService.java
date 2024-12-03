package com.saas.recruitment.service;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.AdvertisementRequest;
import com.saas.recruitment.dto.response.AdvertisementResponse;
import com.saas.recruitment.model.Advertisement;
import com.saas.recruitment.model.MediaType;
import com.saas.recruitment.model.Recruitment;
import com.saas.recruitment.enums.RecruitmentStatus;
import com.saas.recruitment.exception.ResourceExistsException;
import com.saas.recruitment.exception.ResourceNotFoundException;
import com.saas.recruitment.mapper.AdvertisementMapper;
import com.saas.recruitment.repository.AdvertisementRepository;
import com.saas.recruitment.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public AdvertisementResponse createAdvertisement(UUID tenantId,
                                                     AdvertisementRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create advertisement for non-approved recruitment.");
        }
        boolean advertisementExists = advertisementRepository
                .existsByTenantIdAndRecruitment(tenant.getId(), recruitment);
        if (advertisementExists) {
            throw new ResourceExistsException("Advertisement already exists for this recruitment");
        }
        Advertisement advertisement = advertisementMapper.mapToEntity(tenant, recruitment, request);
        advertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.mapToDto(advertisement);
    }

    public List<AdvertisementResponse> getAllAdvertisements(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Advertisement> advertisements = advertisementRepository.findAll();
        return advertisements.stream()
                .filter(adv -> adv.getTenantId().equals(tenant.getId()))
                .map(advertisementMapper::mapToDto)
                .toList();
    }

    public AdvertisementResponse getAdvertisementById(UUID tenantId,
                                                      UUID advertisementId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Advertisement advertisement = getAdvertById(tenant.getId(), advertisementId);
        return advertisementMapper.mapToDto(advertisement);
    }

    public AdvertisementResponse getAdvertisementByVacancyNumber(UUID tenantId,
                                                               String vacancyNumber) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), vacancyNumber);
        Advertisement advertisement = advertisementRepository.findByRecruitmentId(recruitment.getId());
        return advertisementMapper.mapToDto(advertisement);
    }

    @Transactional
    public AdvertisementResponse updateAdvertisement(UUID tenantId,
                                                     UUID advertisementId,
                                                     AdvertisementRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Advertisement advertisement = getAdvertById(tenant.getId(), advertisementId);
        advertisement = advertisementMapper.updateAdvertisement(tenant, advertisement, request);
        advertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.mapToDto(advertisement);
    }

    @Transactional
    public void removeMediaTypeFromAdvertisement(UUID tenantId,
                                                 UUID advertisementId,
                                                 UUID mediaTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Advertisement advertisement = getAdvertById(tenant.getId(), advertisementId);
        MediaType mediaType = validationUtil.getMediaTypeById(tenant.getId(), mediaTypeId);
        advertisement.getMediaTypes().remove(mediaType);
        advertisementRepository.save(advertisement);
    }

    @Transactional
    public void deleteAdvertisement(UUID tenantId,
                                    UUID advertisementId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Advertisement advertisement = getAdvertById(tenant.getId(), advertisementId);
        advertisementRepository.delete(advertisement);
    }

    private Advertisement getAdvertById(UUID tenantId, UUID advertisementId) {

        return advertisementRepository.findById(advertisementId)
                .filter(adv -> adv.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Advertisement not found with id '" + advertisementId + "'"));
    }
}