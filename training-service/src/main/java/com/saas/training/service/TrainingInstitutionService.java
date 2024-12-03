package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.TrainingInstitutionRequest;
import com.saas.training.dto.response.TrainingInstitutionResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.repository.TrainingInstitutionRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.TrainingInstitution;
import com.saas.training.mapper.TrainingInstitutionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingInstitutionService {

    private final TrainingInstitutionRepository trainingInstitutionRepository;
    private final TrainingInstitutionMapper trainingInstitutionMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingInstitutionResponse addTrainingInstitution(UUID tenantId,
                                                              TrainingInstitutionRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (trainingInstitutionRepository.existsByTenantIdAndInstitutionName(
                tenant.getId(), request.getInstitutionName())) {
            throw new ResourceExistsException(
                    "Training institution with name '" + request.getInstitutionName() + "' already exists");
        }
        TrainingInstitution trainingInstitution = trainingInstitutionMapper.mapToEntity(tenant, request);
        trainingInstitution = trainingInstitutionRepository.save(trainingInstitution);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    public List<TrainingInstitutionResponse> getAllTrainingInstitutions(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<TrainingInstitution> trainingInstitutions = trainingInstitutionRepository.findAll();
        return trainingInstitutions.stream()
                .filter(inst -> inst.getTenantId().equals(tenant.getId()))
                .map(trainingInstitutionMapper::mapToDto)
                .toList();
    }

    public TrainingInstitutionResponse getTrainingInstitutionById(UUID tenantId,
                                                                  UUID institutionId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), institutionId);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    @Transactional
    public TrainingInstitutionResponse updateTrainingInstitution(UUID tenantId,
                                                                 UUID institutionId,
                                                                 TrainingInstitutionRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), institutionId);
        if (trainingInstitutionRepository.existsByTenantIdAndInstitutionNameAndIdNot(
                tenant.getId(), request.getInstitutionName(), trainingInstitution.getId())) {
            throw new ResourceExistsException(
                    "Training institution with name '" + request.getInstitutionName() + "' already exists");
        }
        trainingInstitution = trainingInstitutionMapper
                .updateEntity(tenant, trainingInstitution, request);
        trainingInstitution = trainingInstitutionRepository.save(trainingInstitution);
        return trainingInstitutionMapper.mapToDto(trainingInstitution);
    }

    @Transactional
    public void deleteTrainingInstitution(UUID tenantId,
                                          UUID institutionId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingInstitution trainingInstitution = validationUtil
                .getInstitutionById(tenant.getId(), institutionId);
        trainingInstitutionRepository.delete(trainingInstitution);
    }
}