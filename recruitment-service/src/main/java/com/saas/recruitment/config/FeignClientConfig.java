package com.saas.recruitment.config;

import com.saas.recruitment.utility.SecurityUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignClientConfig implements RequestInterceptor {

    private final SecurityUtil securityUtil;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header("Authorization", securityUtil.getUserToken());
    }
}
