package com.saas.leave.service;

import com.saas.leave.client.OrganizationServiceClient;
import com.saas.leave.dto.request.LeaveTypeRequest;

import com.saas.leave.dto.response.LeaveTypeResponse;

import com.saas.leave.model.LeaveType;
import com.saas.leave.exception.ResourceExistsException;
import com.saas.leave.exception.ResourceNotFoundException;
import com.saas.leave.mapper.LeaveTypeMapper;
import com.saas.leave.repository.LeaveTypeRepository;

import com.saas.leave.dto.TenantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public LeaveTypeResponse createLeaveType(UUID tenantId, LeaveTypeRequest leaveTypeRequest) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        if (leaveTypeRepository.existsByLeaveTypeNameAndTenantId(leaveTypeRequest.getLeaveTypeName(),tenant.getId())) {
            throw new ResourceExistsException("LeaveType with name " + leaveTypeRequest.getLeaveTypeName() + " already exists");
        }
        LeaveType leaveType = leaveTypeMapper.toEntity(leaveTypeRequest, tenantId);
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponseDTO(savedLeaveType);
    }

    public List<LeaveTypeResponse> getAllLeaveTypes(UUID tenantId) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();
        return leaveTypes.stream()
                .map(leaveTypeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveTypeResponse getLeaveTypeById(UUID tenantId, UUID id) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        return leaveTypeMapper.toResponseDTO(leaveType);
    }

    public LeaveTypeResponse updateLeaveType(UUID tenantId, UUID id, LeaveTypeRequest leaveTypeRequest) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        leaveTypeMapper.updateLeaveType(leaveType, leaveTypeRequest);
        LeaveType updatedLeaveType = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponseDTO(updatedLeaveType);
    }

    public void deleteLeaveType(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        leaveTypeRepository.delete(leaveType);
    }
}
