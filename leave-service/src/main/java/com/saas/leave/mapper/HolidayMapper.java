package com.saas.leave.mapper;

import com.saas.leave.dto.request.HolidayRequest;
import com.saas.leave.dto.response.HolidayResponse;
import com.saas.leave.model.Holiday;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HolidayMapper {



    /**
     * Maps a HolidayRequest to a Holiday entity.
     *
     * @param request the HolidayRequest to map
     * @return the mapped Holiday entity
     */
    public Holiday mapToEntity(HolidayRequest request) {
        Holiday holiday = new Holiday();
        holiday.setHolidayName(request.getHolidayName());

        return holiday;
    }

    /**
     * Maps a Holiday entity to a HolidayResponse.
     *
     * @param entity the Holiday entity to map
     * @return the mapped HolidayResponse
     */
    public HolidayResponse mapToDto(Holiday entity) {
        HolidayResponse response = new HolidayResponse();
        response.setId(entity.getId());
        response.setHolidayName(entity.getHolidayName());
//        response.setDate(entity.getDate());
        response.setTenantId(entity.getTenantId());

        return response;
    }

    /**
     * Updates an existing Holiday entity with data from a HolidayRequest.
     *
     * @param holiday the existing Holiday entity to update
     * @param request the HolidayRequest containing updated data
     */
    public void updateHoliday(Holiday holiday, HolidayRequest request) {

        if (request.getHolidayName () != null)
            holiday.setHolidayName(request.getHolidayName ());

    }
}

