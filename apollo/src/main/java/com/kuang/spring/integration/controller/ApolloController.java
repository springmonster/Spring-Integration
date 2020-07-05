package com.kuang.spring.integration.controller;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableApolloConfig
public class ApolloController {

    @Value("${sms.enable}")
    private String smsStr;

//    @Resource
//    private DevConfig devConfig;

    @GetMapping("/apollo")
    public String apollo() {
        return "sms.enable " + smsStr;
    }
}
