package com.saas.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String tenantId;
    private Boolean isEnabled;
}
