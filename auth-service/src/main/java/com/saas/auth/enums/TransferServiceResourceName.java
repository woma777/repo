package com.saas.auth.enums;

import lombok.Getter;

@Getter
public enum TransferServiceResourceName {

    /* Training Institution */
    CREAT_TRANSFER("Creat Transfer Request"),
    GET_ALL_TRANSFER_REQUEST("Get All Transfer Request"),
    GET_TRANSFER_REQUEST_BY_ID("Get Transfer Request Details"),
    UPDATE_TRANSFER_REQUEST("Update Transfer Request"),
    DELETE_TRANSFER_REQUEST("Delete Transfer Request"),
    APPROVE_TRANSFER_REQUEST("Approve Transfer Request"),

    /* University */
    CREAT_ASSIGNMENT("Creat Direct Assignment"),
    GET_ALL_ASSIGNMENT("Get All Direct Assignment"),
    GET_ASSIGNMENT_BY_ID("Get  Direct Assignment Details"),
    UPDATE_ASSIGNMENT("Update Direct Assignment Details"),
    DELETE_ASSIGNMENT("Delete Direct Assignment Details"),;


    private final String value;

    TransferServiceResourceName(String value) {
        this.value = value;
    }
}
