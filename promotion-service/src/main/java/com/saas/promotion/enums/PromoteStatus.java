package com.saas.promotion.enums;

import lombok.Getter;

@Getter
public enum PromoteStatus {

    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String value;

    PromoteStatus(String value) {
        this.value = value;
    }
}
