package com.saas.auth.service;

import com.saas.auth.dto.request.GroupRequest;
import com.saas.auth.dto.response.GroupResponse;
import com.saas.auth.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final Keycloak keycloak;
    private final GroupMapper groupMapper;

    @Value("${keycloak.realm}")
    private String realm;

    public GroupResponse addGroup(GroupRequest request) {

        RealmResource realmResource = keycloak.realm(realm);
        GroupsResource groupsResource = realmResource.groups();
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(request.getServiceName());
        groupsResource.add(groupRepresentation);
        return groupMapper.mapToDto(groupRepresentation);
    }

    public List<GroupResponse> getAllGroups() {

        GroupsResource groupsResource = keycloak.realm(realm).groups();
        List<GroupRepresentation> groupRepresentations = groupsResource.groups();
        return groupRepresentations.stream()
                .map(groupMapper::mapToDto)
                .toList();
    }

    public GroupResponse getGroupId(String groupId) {

        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupResource groupResource = groupsResource.group(groupId);
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();
        return groupMapper.mapToDto(groupRepresentation);
    }

    public GroupResponse updateGroup(String groupId, GroupRequest request) {

        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupResource groupResource = groupsResource.group(groupId);
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();
        if (request.getServiceName() != null) {
            groupRepresentation.setName(request.getServiceName());
        }
        groupResource.update(groupRepresentation);
        return groupMapper.mapToDto(groupRepresentation);
    }

    public void deleteGroup(String groupId) {

        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupResource groupResource = groupsResource.group(groupId);
        groupResource.remove();
    }
}
