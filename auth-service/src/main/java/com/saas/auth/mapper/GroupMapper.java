package com.saas.auth.mapper;

import com.saas.auth.dto.response.GroupResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public GroupResponse mapToDto(GroupRepresentation groupRepresentation) {

        GroupResponse response = new GroupResponse();
        response.setId(groupRepresentation.getId());
        response.setServiceName(groupRepresentation.getName());

        return response;
    }
}
