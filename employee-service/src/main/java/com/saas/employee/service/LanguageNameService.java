package com.saas.employee.service;

import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.LanguageNameRequest;
import com.saas.employee.dto.response.LanguageNameResponse;
import com.saas.employee.model.LanguageName;
import com.saas.employee.exception.ResourceExistsException;
import com.saas.employee.mapper.LanguageNameMapper;
import com.saas.employee.repository.LanguageNameRepository;
import com.saas.employee.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LanguageNameService {

    private final LanguageNameRepository languageNameRepository;
    private final LanguageNameMapper languageNameMapper;
    private final ValidationUtil validationUtil;

    public LanguageNameResponse addLanguageName(UUID tenantId,
                                                LanguageNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = languageNameMapper.mapToEntity(tenant, request);
        if (languageNameRepository.existsByLanguageNameAndTenantId(
                request.getLanguageName(), tenant.getId())) {
            throw new ResourceExistsException(
                    "Language with name '" + request.getLanguageName() + "' already exists");
        }
        languageName = languageNameRepository.save(languageName);
        return languageNameMapper.mapToDto(languageName);
    }

    public List<LanguageNameResponse> getAllLanguageNames(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<LanguageName> languageNames = languageNameRepository.findAll();
        return languageNames.stream()
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .map(languageNameMapper::mapToDto)
                .toList();
    }

    public LanguageNameResponse getLanguageNameById(UUID tenantId,
                                                UUID languageId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = validationUtil.getLanguageNameById(tenant.getId(), languageId);
        return languageNameMapper.mapToDto(languageName);
    }

    public LanguageNameResponse updateLanguageName(UUID tenantId,
                                                   UUID languageId,
                                                   LanguageNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = validationUtil.getLanguageNameById(tenant.getId(), languageId);
        if (languageNameRepository.existsByTenantIdAndLanguageNameAndIdNot(
                tenant.getId(), request.getLanguageName(), languageName.getId())) {
            throw new ResourceExistsException(
                    "Language with name '" + request.getLanguageName() + "' already exists");
        }
        languageName = languageNameMapper.updateEntity(languageName, request);
        languageName = languageNameRepository.save(languageName);
        return languageNameMapper.mapToDto(languageName);
    }

    public void deleteLanguageName(UUID tenantId,
                                   UUID languageId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = validationUtil.getLanguageNameById(tenant.getId(), languageId);
        languageNameRepository.delete(languageName);
    }
}