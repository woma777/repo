package com.saas.recruitment.service;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.LanguageRequest;
import com.saas.recruitment.dto.response.LanguageResponse;
import com.saas.recruitment.model.Applicant;
import com.saas.recruitment.model.Language;
import com.saas.recruitment.exception.ResourceNotFoundException;
import com.saas.recruitment.mapper.LanguageMapper;
import com.saas.recruitment.repository.LanguageRepository;
import com.saas.recruitment.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public LanguageResponse addLanguage(UUID tenantId,
                                        UUID applicantId,
                                        LanguageRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Language language = languageMapper.mapToEntity(tenant, applicant, request);
        language = languageRepository.save(language);
        return languageMapper.mapToDto(language);
    }

    public List<LanguageResponse> getAllLanguages(UUID tenantId,
                                                  UUID applicantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        List<Language> languages = languageRepository
                .findByTenantIdAndApplicant(tenant.getId(), applicant);
        return languages.stream()
                .map(languageMapper::mapToDto)
                .toList();
    }

    public LanguageResponse getLanguageById(UUID tenantId,
                                            UUID applicantId,
                                            UUID languageId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Language language = getLanguageById(tenant.getId(), applicant, languageId);
        return languageMapper.mapToDto(language);
    }

    @Transactional
    public LanguageResponse updateLanguage(UUID tenantId,
                                           UUID applicantId,
                                           UUID languageId,
                                           LanguageRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Language language = getLanguageById(tenant.getId(), applicant, languageId);
        language = languageMapper.updateLanguage(tenant, language, request);
        language = languageRepository.save(language);
        return languageMapper.mapToDto(language);
    }

    @Transactional
    public void deleteLanguage(UUID tenantId,
                               UUID applicantId,
                               UUID languageId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Applicant applicant = validationUtil.getApplicantById(tenant.getId(), applicantId);
        Language language = getLanguageById(tenant.getId(), applicant, languageId);
        languageRepository.delete(language);
    }

    private Language getLanguageById(UUID tenantId, Applicant applicant, UUID languageId) {

        return languageRepository.findById(languageId)
                .filter(lan -> lan.getTenantId().equals(tenantId))
                .filter(lan -> lan.getApplicant().equals(applicant))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + languageId + "'"));
    }
}