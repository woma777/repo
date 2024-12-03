package com.saas.employee.service;

import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.DutyStationRequest;
import com.saas.employee.dto.response.DutyStationResponse;
import com.saas.employee.exception.ResourceExistsException;
import com.saas.employee.mapper.DutyStationMapper;
import com.saas.employee.model.DutyStation;
import com.saas.employee.repository.DutyStationRepository;
import com.saas.employee.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DutyStationService {

    private final DutyStationRepository dutyStationRepository;
    private final DutyStationMapper dutyStationMapper;
    private final ValidationUtil validationUtil;

    public DutyStationResponse addDutyStation(UUID tenantId,
                                              DutyStationRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (dutyStationRepository.existsByTenantIdAndName(tenant.getId(), request.getName())) {
            throw new ResourceExistsException("Duty station already exists");
        }
        DutyStation dutyStation = dutyStationMapper.mapToEntity(tenant, request);
        dutyStation = dutyStationRepository.save(dutyStation);
        return dutyStationMapper.mapToDto(dutyStation);
    }

    public List<DutyStationResponse> getAllDutyStations(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<DutyStation> dutyStations = dutyStationRepository.findByTenantId(tenantId);
        return dutyStations.stream()
                .map(dutyStationMapper::mapToDto)
                .toList();
    }

    public DutyStationResponse getDutyStationById(UUID tenantId,
                                                  UUID stationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DutyStation dutyStation = validationUtil.getDutyStationById(tenantId, stationId);
        return dutyStationMapper.mapToDto(dutyStation);
    }

    public DutyStationResponse updateDutyStation(UUID tenantId,
                                                 UUID stationId,
                                                 DutyStationRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DutyStation dutyStation = validationUtil.getDutyStationById(tenantId, stationId);
        if (dutyStationRepository.existsByTenantIdAndNameAndIdNot(
                tenant.getId(), request.getName(), dutyStation.getId())) {
            throw new ResourceExistsException("Duty station already exists");
        }
        dutyStation = dutyStationRepository.save(dutyStation);
        return dutyStationMapper.mapToDto(dutyStation);
    }

    public void deleteDutyStation(UUID tenantId,
                                  UUID stationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DutyStation dutyStation = validationUtil.getDutyStationById(tenantId, stationId);
        dutyStationRepository.delete(dutyStation);
    }
}
