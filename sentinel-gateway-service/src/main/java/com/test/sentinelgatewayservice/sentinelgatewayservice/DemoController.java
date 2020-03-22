package com.test.sentinelgatewayservice.sentinelgatewayservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
