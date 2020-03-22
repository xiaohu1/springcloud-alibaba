package com.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
public class ConfigContrller {

    @Value("${configUrl: null}")
    private String configUrl;

    @GetMapping("/url")
    public String getConfigUrl(){
        return configUrl;
    }
}
