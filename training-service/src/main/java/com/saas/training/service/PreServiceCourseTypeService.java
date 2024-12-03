package com.saas.training.service;

import com.saas.training.dto.clientDto.TenantDto;
import com.saas.training.dto.request.PreServiceCourseTypeRequest;
import com.saas.training.dto.response.PreServiceCourseTypeResponse;
import com.saas.training.exception.ResourceExistsException;
import com.saas.training.repository.PreServiceCourseTypeRepository;
import com.saas.training.utility.ValidationUtil;
import com.saas.training.model.PreServiceCourseType;
import com.saas.training.mapper.PreServiceCourseTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreServiceCourseTypeService {

    private final PreServiceCourseTypeRepository preServiceCourseTypeRepository;
    private final PreServiceCourseTypeMapper preServiceCourseTypeMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public PreServiceCourseTypeResponse addCourseType(UUID tenantId,
                                                      PreServiceCourseTypeRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType courseType = preServiceCourseTypeMapper.mapToEntity(tenant, request);
        if (preServiceCourseTypeRepository.existsByTenantIdAndCourseType(
                tenant.getId(), request.getCourseType())) {
            throw new ResourceExistsException(
                    "Course type with name '" + request.getCourseType() + "' already exists");
        }
        courseType = preServiceCourseTypeRepository.save(courseType);
        return preServiceCourseTypeMapper.mapToDto(courseType);
    }

    public List<PreServiceCourseTypeResponse> getAllCourseTypes(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<PreServiceCourseType> preServiceCourseTypes = preServiceCourseTypeRepository.findAll();
        return preServiceCourseTypes.stream()
                .filter(type -> type.getTenantId().equals(tenant.getId()))
                .map(preServiceCourseTypeMapper::mapToDto)
                .toList();
    }

    public PreServiceCourseTypeResponse getCourseTypeById(UUID tenantId,
                                                          UUID courseTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), courseTypeId);
        return preServiceCourseTypeMapper.mapToDto(preServiceCourseType);
    }

    @Transactional
    public PreServiceCourseTypeResponse updateCourseType(UUID tenantId,
                                                         UUID courseTypeId,
                                                         PreServiceCourseTypeRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), courseTypeId);
        if (preServiceCourseTypeRepository.existsByTenantIdAndCourseTypeAndIdNot(
                tenant.getId(), request.getCourseType(), preServiceCourseType.getId())) {
            throw new ResourceExistsException(
                    "Course type with name '" + request.getCourseType() + "' already exists");
        }
        preServiceCourseType = preServiceCourseTypeMapper.updateEntity(preServiceCourseType, request);
        preServiceCourseType = preServiceCourseTypeRepository.save(preServiceCourseType);
        return preServiceCourseTypeMapper.mapToDto(preServiceCourseType);
    }

    @Transactional
    public void deleteCourseType(UUID tenantId,
                                 UUID courseTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), courseTypeId);
        preServiceCourseTypeRepository.delete(preServiceCourseType);
    }
}