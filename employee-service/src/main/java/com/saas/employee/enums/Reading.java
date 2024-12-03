package com.saas.employee.enums;

import lombok.Getter;

@Getter
public enum Reading {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    PROFICIENT("Proficient");

    private final String reading;

    Reading(String reading) {
        this.reading = reading;
    }
}
