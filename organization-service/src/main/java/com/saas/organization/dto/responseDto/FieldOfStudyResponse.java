package com.saas.organization.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudyResponse {
    private UUID id;
    private String fieldOfStudy;
    private String description;
    private UUID tenantId;

    // Getters and Setters
}
