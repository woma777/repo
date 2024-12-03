package com.saas.recruitment.mapper;

import com.saas.recruitment.dto.clientDto.TenantDto;
import com.saas.recruitment.dto.request.MediaTypeRequest;
import com.saas.recruitment.dto.response.MediaTypeResponse;
import com.saas.recruitment.model.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MediaTypeMapper {

    public MediaType mapToEntity(TenantDto tenant,
                                 MediaTypeRequest request) {

        MediaType mediaType = new MediaType();
        mediaType.setTenantId(tenant.getId());
        mediaType.setMediaTypeName(request.getMediaTypeName());
        mediaType.setDescription(request.getDescription());

        return mediaType;
    }

    public MediaTypeResponse mapToDto(MediaType mediaType) {

        MediaTypeResponse response = new MediaTypeResponse();
        response.setId(mediaType.getId());
        response.setMediaTypeName(mediaType.getMediaTypeName());
        response.setDescription(mediaType.getDescription());
        response.setTenantId(mediaType.getTenantId());
        response.setCreatedAt(mediaType.getCreatedAt());
        response.setUpdatedAt(mediaType.getUpdatedAt());
        response.setCreatedBy(mediaType.getCreatedBy());
        response.setUpdatedBy(mediaType.getUpdatedBy());

        return response;
    }

    public MediaType updateEntity(MediaType mediaType,
                                  MediaTypeRequest request) {

        if (request.getMediaTypeName() != null) {
            mediaType.setMediaTypeName(request.getMediaTypeName());
        }
        if (request.getDescription() != null) {
            mediaType.setDescription(request.getDescription());
        }

        return mediaType;
    }
}
