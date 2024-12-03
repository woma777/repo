package com.saas.employee.service;

import com.saas.employee.dto.clientDto.TenantDto;
import com.saas.employee.dto.request.CountryRequest;
import com.saas.employee.dto.response.CountryResponse;
import com.saas.employee.mapper.CountryMapper;
import com.saas.employee.model.Country;
import com.saas.employee.repository.CountryRepository;
import com.saas.employee.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final ValidationUtil validationUtil;

    public CountryResponse addCountry(UUID tenantId,
                                      CountryRequest request) {


        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Country country = countryMapper.mapToEntity(tenant, request);
        country = countryRepository.save(country);
        return countryMapper.mapToDto(country);
    }

    public List<CountryResponse> getAllCountries(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Country> countries = countryRepository.findByTenantId(tenant.getId());
        return countries.stream()
                .map(countryMapper::mapToDto)
                .toList();
    }

    public CountryResponse getCountryById(UUID tenantId,
                                          UUID countryId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Country country = validationUtil.getCountryById(tenant.getId(), countryId);
        return countryMapper.mapToDto(country);
    }

    public CountryResponse updateCountry(UUID tenantId,
                                         UUID countryId,
                                         CountryRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Country country = validationUtil.getCountryById(tenant.getId(), countryId);
        country = countryMapper.updateEntity(country, request);
        return countryMapper.mapToDto(country);
    }

    public void deleteCountry(UUID tenantId,
                              UUID countryId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Country country = validationUtil.getCountryById(tenant.getId(), countryId);
        countryRepository.delete(country);
    }
}
