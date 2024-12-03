package com.saas.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceResponse extends BaseResponse {
    private String fullName;
    private String phoneNumber;
    private String jobTitle;
    private String workAddress;
    private String email;
    private String description;
    private UUID applicantId;
}
