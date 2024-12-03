package com.saas.employee.mapper;

import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.LanguageNameRequest;
import com.saas.employee.dto.response.LanguageNameResponse;
import com.saas.employee.model.LanguageName;
import org.springframework.stereotype.Component;

@Component
public class LanguageNameMapper {

    public LanguageName mapToEntity(TenantDto tenant,
                                    LanguageNameRequest request) {

        LanguageName languageName = new LanguageName();
        languageName.setTenantId(tenant.getId());
        languageName.setLanguageName(request.getLanguageName());
        languageName.setDescription(request.getDescription());

        return languageName;
    }

    public LanguageNameResponse mapToDto(LanguageName languageName) {

        LanguageNameResponse response = new LanguageNameResponse();
        response.setId(languageName.getId());
        response.setLanguageName(languageName.getLanguageName());
        response.setDescription(languageName.getDescription());
        response.setTenantId(languageName.getTenantId());
        response.setCreatedAt(languageName.getCreatedAt());
        response.setUpdatedAt(languageName.getUpdatedAt());
        response.setCreatedAt(languageName.getCreatedAt());
        response.setUpdatedAt(languageName.getUpdatedAt());

        return response;
    }

    public LanguageName updateEntity(LanguageName languageName,
                                     LanguageNameRequest request) {

        if (request.getLanguageName() != null) {
            languageName.setLanguageName(request.getLanguageName());
        }
        if (request.getDescription() != null) {
            languageName.setDescription(request.getDescription());
        }

        return languageName;
    }
}
