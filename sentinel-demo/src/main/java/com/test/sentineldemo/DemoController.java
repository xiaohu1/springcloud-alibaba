package com.test.sentineldemo;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/test")
    public String demo(){
        return "hello sentinel";
    }

    @GetMapping("/resource")
    @SentinelResource(value = "hello")
    public String resource(){
        return "sentinel resources";
    }
}
