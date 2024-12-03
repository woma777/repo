package com.saas.organization.mapper;

import com.saas.organization.dto.requestDto.LocationTypeRequest;
import com.saas.organization.dto.responseDto.LocationTypeResponse;
import com.saas.organization.model.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationTypeMapper {
    @Autowired
    private TenantMapper tenantMapper;
    public LocationType mapToEntity(LocationTypeRequest locationTypeRequest) {
        LocationType location = new LocationType ();
        location.setLocationTypeName(locationTypeRequest.getLocationTypeName());
        location.setDescription(locationTypeRequest.getDescription());
        return location;
    }

    public LocationTypeResponse mapToDto(LocationType locationType) {
        LocationTypeResponse response = new LocationTypeResponse ();
        response.setId(locationType.getId());
        response.setLocationTypeName(locationType.getLocationTypeName());
        response.setDescription(locationType.getDescription());
        response.setTenantId(locationType.getTenant().getId());

        return response;
    }

    public LocationType updateLocationType(LocationType locationType, LocationTypeRequest locationTypeRequest) {
        if (locationTypeRequest.getLocationTypeName () != null)
            locationType.setLocationTypeName (locationTypeRequest.getLocationTypeName ());
//        if (locationTypeRequest.getDescription () != null)
//            locationType.setDescription (locationType.getDescription());
        if (locationTypeRequest.getDescription () != null)
            locationType.setDescription (locationTypeRequest.getDescription());


        return locationType;
    }
}

