package com.saas.recruitment.dto.clientDto;

import com.saas.recruitment.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto extends BaseResponse {

    private String name;
    private String abbreviatedName;
    private String code;
}
