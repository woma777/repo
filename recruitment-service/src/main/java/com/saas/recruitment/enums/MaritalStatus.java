package com.saas.recruitment.enums;

import lombok.Getter;

@Getter
public enum MaritalStatus {

    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed"),
    SEPARATED("Separated"),
    OTHER("Other");

    private final String maritalStatus;

    MaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
