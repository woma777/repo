package com.saas.organization.service;

import com.saas.organization.dto.requestDto.DepartmentTypeRequest;
import com.saas.organization.dto.responseDto.DepartmentTypeResponse;
import com.saas.organization.model.DepartmentType;
import com.saas.organization.model.Tenant;
import com.saas.organization.exception.ResourceExistsException;
import com.saas.organization.exception.ResourceNotFoundException;
import com.saas.organization.mapper.DepartmentTypeMapper;
import com.saas.organization.repository.DepartmentRepository;
import com.saas.organization.repository.DepartmentTypeRepository;
import com.saas.organization.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentTypeService {

        private final DepartmentTypeRepository departmentTypeRepository;
        private final DepartmentTypeMapper departmentTypeMapper;
        private final TenantRepository tenantRepository;
        private final DepartmentRepository departmentRepository;

        public DepartmentTypeResponse createDepartmentType(UUID tenantId,
                                                           DepartmentTypeRequest departmentTypeRequest) {
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Tenant not found with id: " + tenantId + " "));
                if (departmentTypeRepository.existsByDepartmentTypeNameAndTenantId(
                        departmentTypeRequest.getDepartmentTypeName(), tenant.getId())) {
                        throw new ResourceExistsException("Department type with Name " +
                                departmentTypeRequest.getDepartmentTypeName() + " already exists");
                }
                DepartmentType departmentType = departmentTypeMapper.mapToEntity(departmentTypeRequest);
                departmentType.setTenant(tenant);
                departmentType = departmentTypeRepository.save(departmentType);
                return departmentTypeMapper.mapToDto(departmentType);
        }

        public List<DepartmentTypeResponse> getAllDepartmentTypes(UUID tenantId) {
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Tenant not found with id: " + tenantId + " "));
                List<DepartmentType> departmentTypes = departmentTypeRepository.findAll();
                return departmentTypes.stream()
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .map(departmentTypeMapper::mapToDto)
                        .collect(Collectors.toList());
        }

        public DepartmentTypeResponse getDepartmentTypeById(UUID id, UUID tenantId) {
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Tenant not found with id: " + tenantId + " "));
                DepartmentType departmentType = departmentTypeRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "DepartmentType not found with id: " + id + " for the specified tenant "));
                return departmentTypeMapper.mapToDto(departmentType);
        }

        public DepartmentTypeResponse updateDepartmentType(UUID id, UUID tenantId, DepartmentTypeRequest departmentTypeRequest) {
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Tenant not found with id: " + tenantId + " "));
                DepartmentType departmentType = departmentTypeRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "DepartmentType not found with id: " + id + " for the specified tenant "));
                departmentType = departmentTypeMapper.updateDepartmentType(departmentType, departmentTypeRequest);
                departmentType = departmentTypeRepository.save(departmentType);
                return departmentTypeMapper.mapToDto(departmentType);
        }

        public void deleteDepartmentType(UUID id, UUID tenantId) {
                Tenant tenant = tenantRepository.findById(tenantId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Tenant not found with id: " + tenantId + " "));
                DepartmentType departmentType = departmentTypeRepository.findById(id)
                        .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "DepartmentType not found with id: " + id + " for the specified tenant "));
                departmentTypeRepository.delete(departmentType);
        }
}