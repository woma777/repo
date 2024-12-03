package com.saas.employee.service;

import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.TitleNameRequest;
import com.saas.employee.dto.response.TitleNameResponse;
import com.saas.employee.model.TitleName;
import com.saas.employee.exception.ResourceExistsException;
import com.saas.employee.mapper.TitleNameMapper;
import com.saas.employee.repository.TitleNameRepository;
import com.saas.employee.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TitleNameService {

    private final TitleNameRepository titleNameRepository;
    private final TitleNameMapper titleNameMapper;
    private final ValidationUtil validationUtil;

    public TitleNameResponse addTitleName(UUID tenantId,
                                          TitleNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = titleNameMapper.mapToEntity(tenant, request);
        if (titleNameRepository.existsByTitleNameAndTenantId(
                request.getTitleName(), tenant.getId())) {
            throw new ResourceExistsException(
                    "Title name '" + request.getTitleName() + "' already exists");
        }
        titleName = titleNameRepository.save(titleName);
        return titleNameMapper.mapToDto(titleName);
    }

    public List<TitleNameResponse> getAllTitleNames(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<TitleName> titleNames = titleNameRepository.findAll();
        return titleNames.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(titleNameMapper::mapToDto)
                .toList();
    }

    public TitleNameResponse getTitleNameById(UUID tenantId,
                                          UUID titleId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), titleId);
        return titleNameMapper.mapToDto(titleName);
    }

    public TitleNameResponse updateTitleName(UUID tenantId,
                                             UUID titleId,
                                             TitleNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), titleId);
        if (titleNameRepository.existsByTenantIdAndTitleNameAndIdNot(
                tenant.getId(), request.getTitleName(), titleName.getId())) {
            throw new ResourceExistsException(
                    "Title name '" + request.getTitleName() + "' already exists");
        }
        titleName = titleNameMapper.updateEntity(titleName, request);
        titleName = titleNameRepository.save(titleName);
        return titleNameMapper.mapToDto(titleName);
    }

    public void deleteTitleName(UUID tenantId,
                                UUID titleId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), titleId);
        titleNameRepository.delete(titleName);
    }
}