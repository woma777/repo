package com.saas.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse  extends BaseResponse{
    private String addressType;
    private UUID locationId;
    private String houseNumber;
    private String homeTelephone;
    private String officeTelephone;
    private String mobileNumber;
    private String email;
    private String poBox;
    private UUID employeeId;
}
