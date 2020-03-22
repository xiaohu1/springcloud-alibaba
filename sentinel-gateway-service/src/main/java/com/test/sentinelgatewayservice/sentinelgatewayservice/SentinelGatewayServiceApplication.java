package com.test.sentinelgatewayservice.sentinelgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SentinelGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelGatewayServiceApplication.class, args);
    }

}
