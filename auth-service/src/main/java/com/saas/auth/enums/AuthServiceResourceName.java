package com.saas.auth.enums;

import lombok.Getter;

@Getter
public enum AuthServiceResourceName {

    /* User */
    ADD_USER("Add User"),
    GET_ALL_USERS("Get All Users"),
    GET_USERS_BY_ROLE_NAME("Get Users By Role Name"),
    GET_USER_ROLES("Get User Roles"),
    GET_USER_BY_ID("Get User Details"),
    GET_USER_BY_USERNAME("Get User by Username"),
    UPDATE_USER("Update User"),
    ENABLE_USER("Enable User"),
    DISABLE_USER("Disable User"),
    RESET_PASSWORD("Reset Password"),
    SEND_VERIFICATION_EMAIL("Send Verification Email"),
    FORGOT_PASSWORD("Forgot Password"),
    DELETE_USER("Delete User"),

    /* Role */
    ADD_ROLE("Add Role"),
    ASSIGN_ROLE_TO_USER("Assign Role to User"),
    GET_ALL_ROLES("Get All Roles"),
    GET_ROLE_BY_NAME("Get Role Details"),
    UPDATE_ROLE("Update Role"),
    UNASSIGN_ROLE_FROM_USER("Unassign Role from User"),
    DELETE_ROLE("Delete Role"),

    /* Resource */
    ADD_RESOURCE("Add Resource"),
    GET_ALL_RESOURCES("Get All Resources"),
    GET_RESOURCES_BY_ROLE_NAME("Get Resources by Role"),
    GET_RESOURCE_BY_ID("Get Resource Details"),
    GET_RESOURCE_BY_NAME("Get Resource by Name"),
    GET_RESOURCES_BY_SERVICE_NAME("Get Resources by Service Name"),
    UPDATE_RESOURCE("Update Resource"),
    GRANT_RESOURCE_ACCESS_TO_ROLE("Grant Resource Access to Role"),
    REVOKE_RESOURCE_ACCESS_FROM_ROLE("Revoke Resource Access from Role"),
    DELETE_RESOURCE("Delete Resource");

    private final String value;

    AuthServiceResourceName(String value) {
        this.value = value;
    }
}
