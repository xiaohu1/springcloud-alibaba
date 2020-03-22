package com.test.sentinelfeignConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class SentinelFeignDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelFeignDemoApplication.class, args);
    }

}
