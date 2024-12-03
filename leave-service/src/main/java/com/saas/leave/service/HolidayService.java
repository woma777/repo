package com.saas.leave.service;

import com.saas.leave.client.OrganizationServiceClient;
import com.saas.leave.dto.TenantDto;
import com.saas.leave.dto.request.HolidayRequest;
import com.saas.leave.dto.response.HolidayResponse;
import com.saas.leave.model.Holiday;
import com.saas.leave.exception.ResourceExistsException;
import com.saas.leave.exception.ResourceNotFoundException;
import com.saas.leave.mapper.HolidayMapper;
import com.saas.leave.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public HolidayResponse createHoliday(UUID tenantId, HolidayRequest holidayRequest) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        Holiday holiday = holidayMapper.mapToEntity(holidayRequest);
        if (tenantId == null ) {
            throw new IllegalArgumentException("TenantId  must not be null");
        }
        holiday.setTenantId(tenant.getId());

        if (holidayRepository.existsByHolidayNameAndTenantId(holidayRequest.getHolidayName(),tenant.getId())) {
            throw new ResourceExistsException("Holiday with Name " + holidayRequest.getHolidayName() + " already exists");
        }

        Holiday savedHoliday = holidayRepository.save(holiday);
        return holidayMapper.mapToDto(savedHoliday);
    }

    public List<HolidayResponse> getAllHolidays(UUID tenantId) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        List<Holiday> holidays = holidayRepository.findAll();
        return holidays.stream()
                .map(holidayMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HolidayResponse getHolidayById(UUID tenantId, UUID id) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        return holidayMapper.mapToDto(holiday);
    }

    public HolidayResponse updateHoliday(UUID tenantId, UUID id, HolidayRequest holidayRequest) {
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        holidayMapper.updateHoliday(holiday, holidayRequest);
        Holiday updatedHoliday = holidayRepository.save(holiday);
        return holidayMapper.mapToDto(updatedHoliday);
    }

    public void deleteHoliday(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id " + id));
        holidayRepository.delete(holiday);
    }
}
