package com.test.gateway;

import com.test.gateway.filters.AuthGatewayFilter;
import com.test.gateway.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = "com.test.authclient")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter();
    }

    @Bean
    public AuthGatewayFilter authGatewayFilter(){
        return new AuthGatewayFilter();
    }

}
