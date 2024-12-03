package com.saas.planning.service;

import com.saas.planning.client.LeaveServiceClient;
import com.saas.planning.client.OrganizationServiceClient;
import com.saas.planning.dto.BudgetYearDto;
import com.saas.planning.dto.TenantDto;
import com.saas.planning.dto.WorkUnitDto;
import com.saas.planning.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.saas.planning.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.saas.planning.model.AnnualRecruitmentAndPromotion;
import com.saas.planning.model.HrNeedRequest;
import com.saas.planning.exception.ResourceNotFoundException;
import com.saas.planning.mapper.AnnualRecruitmentAndPromotionMapper;
import com.saas.planning.repository.AnnualRecruitmentAndPromotionRepository;
import com.saas.planning.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnualRecruitmentAndPromotionService {

    private final AnnualRecruitmentAndPromotionRepository repository;
    private final NeedRequestRepository needRequestRepository;
    private final AnnualRecruitmentAndPromotionMapper mapper;
    private final OrganizationServiceClient organizationServiceClient;
    private final LeaveServiceClient leaveServiceClient;

    public AnnualRecruitmentAndPromotionResponse createAnnualRecruitmentAndPromotion(UUID tenantId, AnnualRecruitmentAndPromotionRequest request) {
        validateInputs(tenantId, request);

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, request.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(request.getWorkUnitId(), tenantId);
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        HrNeedRequest hrNeedRequest = fetchHrNeedRequest(request.getHrNeedRequestId());

        AnnualRecruitmentAndPromotion entity = mapper.toEntity(request);
        entity.setHrNeedRequest(hrNeedRequest);
        entity.setTenantId(tenant.getId());
        entity.setProcessedDate(LocalDate.now()); // Set current date as processed date

        AnnualRecruitmentAndPromotion savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    public List<AnnualRecruitmentAndPromotionResponse> getAllAnnualRecruitmentAndPromotions(UUID tenantId) {
        validateTenantId(tenantId);

        List<AnnualRecruitmentAndPromotion> entities = repository.findAll();
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public AnnualRecruitmentAndPromotionResponse getAnnualRecruitmentAndPromotionById(UUID tenantId, UUID id) {
        validateTenantId(tenantId);

        AnnualRecruitmentAndPromotion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));
        return mapper.toDto(entity);
    }

    public AnnualRecruitmentAndPromotionResponse updateAnnualRecruitmentAndPromotion(UUID tenantId, UUID id, AnnualRecruitmentAndPromotionRequest request) {
        validateInputs(tenantId, request);

        AnnualRecruitmentAndPromotion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, request.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(request.getWorkUnitId(), tenantId);

        HrNeedRequest hrNeedRequest = fetchHrNeedRequest(request.getHrNeedRequestId());

        mapper.updateEntity(entity, request);
        entity.setHrNeedRequest(hrNeedRequest);
        entity.setTenantId(tenantId); // Assuming tenantId remains unchanged in updates

        AnnualRecruitmentAndPromotion updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    public void deleteAnnualRecruitmentAndPromotion(UUID tenantId, UUID id) {
        validateTenantId(tenantId);

        AnnualRecruitmentAndPromotion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));
        repository.delete(entity);
    }

    private void validateInputs(UUID tenantId, AnnualRecruitmentAndPromotionRequest request) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (request == null || request.getBudgetYearId() == null
                || request.getWorkUnitId() == null
                || request.getHrNeedRequestId() == null) {
            throw new IllegalArgumentException("AnnualRecruitmentAndPromotionRequest and its fields must not be null");
        }
    }

    private void validateTenantId(UUID tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
    }

    private HrNeedRequest fetchHrNeedRequest(UUID hrNeedRequestId) {
        return needRequestRepository.findById(hrNeedRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("HrNeedRequest not found with id " + hrNeedRequestId));
    }
}
