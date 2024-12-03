package com.saas.planning.service;

//import com.insa.hr_planning_service.dto.request.HrAnalysisRequestDTO;
import com.saas.planning.client.LeaveServiceClient;
import com.saas.planning.client.OrganizationServiceClient;
import com.saas.planning.dto.BudgetYearDto;
import com.saas.planning.dto.JobRegistrationDto;
import com.saas.planning.dto.TenantDto;
import com.saas.planning.dto.WorkUnitDto;
import com.saas.planning.dto.request.HrAnalysisRequestDto;
//import com.insa.hr_planning_service.dto.response.HrAnalysisResponseDTO;
import com.saas.planning.dto.response.HrAnalysisResponseDto;
import com.saas.planning.model.HrAnalysis;
import com.saas.planning.exception.ResourceNotFoundException;
import com.saas.planning.mapper.HrAnalysisMapper;
import com.saas.planning.repository.HrAnalysisRepository;
import com.saas.planning.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HrAnalysisService {

    private final HrAnalysisRepository hrAnalysisRepository;
    private final HrAnalysisMapper hrAnalysisMapper;
    private final OrganizationServiceClient organizationServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final NeedRequestRepository needRequestRepository;

    public HrAnalysisResponseDto createHrAnalysis(UUID tenantId, HrAnalysisRequestDto hrAnalysisRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (hrAnalysisRequest == null || hrAnalysisRequest.getBudgetYearId() == null
                || hrAnalysisRequest.getWorkUnitId() == null
                || hrAnalysisRequest.getJobRegistrationId() == null) {
            throw new IllegalArgumentException("HrAnalysisRequest and its fields must not be null");
        }

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, hrAnalysisRequest.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(hrAnalysisRequest.getWorkUnitId(), tenantId);
        JobRegistrationDto jobRegistration = organizationServiceClient.getJobById(hrAnalysisRequest.getJobRegistrationId(), tenantId);
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        HrAnalysis hrAnalysis = hrAnalysisMapper.mapToEntity(hrAnalysisRequest);

        hrAnalysis.setTenantId(tenant.getId());
        hrAnalysis.setBudgetYearId(budgetYear.getId());
        hrAnalysis.setWorkUnitId(workUnit.getId());
        hrAnalysis.setJobRegistrationId(jobRegistration.getId());

        HrAnalysis savedHrAnalysis = hrAnalysisRepository.save(hrAnalysis);
        return hrAnalysisMapper.mapToDto(savedHrAnalysis);
    }

    public List<HrAnalysisResponseDto> getAllHrAnalyses(UUID tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        List<HrAnalysis> hrAnalyses = hrAnalysisRepository.findAll();
        return hrAnalyses.stream()
                .map(hrAnalysisMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HrAnalysisResponseDto getHrAnalysisById(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));
        return hrAnalysisMapper.mapToDto(hrAnalysis);
    }

    public HrAnalysisResponseDto updateHrAnalysis(UUID tenantId, UUID id, HrAnalysisRequestDto hrAnalysisRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (hrAnalysisRequest == null || hrAnalysisRequest.getBudgetYearId() == null
                || hrAnalysisRequest.getWorkUnitId() == null
                || hrAnalysisRequest.getJobRegistrationId() == null) {
            throw new IllegalArgumentException("HrAnalysisRequest and its fields must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, hrAnalysisRequest.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(hrAnalysisRequest.getWorkUnitId(), tenantId);
        JobRegistrationDto jobRegistration = organizationServiceClient.getJobById(hrAnalysisRequest.getJobRegistrationId(), tenantId);

        hrAnalysisMapper.updateHrAnalysis(hrAnalysis, hrAnalysisRequest);

        hrAnalysis.setBudgetYearId(budgetYear.getId());
        hrAnalysis.setWorkUnitId(workUnit.getId());
        hrAnalysis.setJobRegistrationId(jobRegistration.getId());

        HrAnalysis updatedHrAnalysis = hrAnalysisRepository.save(hrAnalysis);
        return hrAnalysisMapper.mapToDto(updatedHrAnalysis);
    }

    public void deleteHrAnalysis(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));
        hrAnalysisRepository.delete(hrAnalysis);
    }
}
