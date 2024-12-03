package com.saas.training.enums;

import lombok.Getter;

@Getter
public enum Decision {

    PASSED("Passed"),
    FAILED("Failed");

    private final String decision;

    Decision(String decision) {
        this.decision = decision;
    }
}
