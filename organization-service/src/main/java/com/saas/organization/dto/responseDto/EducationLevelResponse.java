package com.saas.organization.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevelResponse {
    private UUID id;
    private String educationLevelName;
    private String description;
    private UUID tenantId;

}