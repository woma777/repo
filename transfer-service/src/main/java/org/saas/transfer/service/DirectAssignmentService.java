package org.saas.transfer.service;

import lombok.RequiredArgsConstructor;
import org.saas.transfer.dto.clientDto.TenantDto;
import org.saas.transfer.dto.request.AssignmentRequestDto;
import org.saas.transfer.dto.response.AssignmentResponseDto;
import org.saas.transfer.mapper.DirectAssignmentMapper;
import org.saas.transfer.model.DirectAssignment;
import org.saas.transfer.repository.DirectAssignmentRepository;
import org.saas.transfer.utility.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DirectAssignmentService {
    private final DirectAssignmentRepository directAssignmentRepository;
    private final ValidationUtil validationUtil;
    private final DirectAssignmentMapper directAssignmentMapper;

    public AssignmentResponseDto createAssignment(UUID tenantId, AssignmentRequestDto assignmentRequestDto) {
        TenantDto tenantDto = validationUtil.getTenantById(tenantId);
        DirectAssignment directAssignment = directAssignmentMapper.mapToEntity(tenantDto, assignmentRequestDto);
        directAssignmentRepository.save(directAssignment);
        return directAssignmentMapper.mapToDto(directAssignment);
    }

    public List<AssignmentResponseDto> getAllAssignments(UUID tenantId) {
        TenantDto tenantDto = validationUtil.getTenantById(tenantId);
        List<DirectAssignment> directAssignments = directAssignmentRepository.findAllByTenantId(tenantDto.getId());
        return directAssignments.stream()
                .filter(s -> s.getTenantId().equals(tenantDto.getId()))
                .map(directAssignmentMapper::mapToDto)
                .toList();
    }

    public AssignmentResponseDto getAssignmentById(UUID tenantId, UUID directAssignmentId) {
        TenantDto tenantDto = validationUtil.getTenantById(tenantId);
        DirectAssignment directAssignment = directAssignmentRepository
                .findByTenantIdAndId(tenantDto.getId(), directAssignmentId);
        return directAssignmentMapper.mapToDto(directAssignment);
    }

    public AssignmentResponseDto updateDirectAssignment(UUID tenantId, UUID directAssignmentId, AssignmentRequestDto assignmentRequestDto) {
        TenantDto tenantDto = validationUtil.getTenantById(tenantId);
        DirectAssignment directAssignment = directAssignmentRepository
                .findByTenantIdAndId(tenantDto.getId(), directAssignmentId);
        directAssignment = directAssignmentMapper.updateEntity(directAssignment, assignmentRequestDto);
        directAssignmentRepository.save(directAssignment);
        return directAssignmentMapper.mapToDto(directAssignment);
    }

    public void deleteDirectAssignment(UUID tenantId, UUID directAssignmentId) {
        TenantDto tenantDto = validationUtil.getTenantById(tenantId);
        DirectAssignment directAssignment = directAssignmentRepository
                .findByTenantIdAndId(tenantDto.getId(), directAssignmentId);
        directAssignmentRepository.delete(directAssignment);
    }
}
