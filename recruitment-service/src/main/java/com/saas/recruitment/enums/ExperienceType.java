package com.saas.recruitment.enums;

import lombok.Getter;

@Getter
public enum ExperienceType {

    DIRECT("Direct"),
    RELATED("Related");

    private final String experienceType;

    ExperienceType(String experienceType) {
        this.experienceType = experienceType;
    }
}
