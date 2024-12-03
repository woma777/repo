package com.saas.recruitment.enums;

import lombok.Getter;

@Getter
public enum AnnouncementType {

    INTERNAL("Internal"),
    EXTERNAL("External");

    private final String announcementType;

    AnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }
}
