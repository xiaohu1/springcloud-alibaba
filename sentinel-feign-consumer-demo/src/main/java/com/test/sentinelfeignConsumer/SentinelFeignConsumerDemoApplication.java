package com.test.sentinelfeignConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class SentinelFeignConsumerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelFeignConsumerDemoApplication.class, args);
    }

}
