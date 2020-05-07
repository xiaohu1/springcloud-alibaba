package com.test.gateway;

import com.test.gateway.filters.AuthGatewayFilter;
import com.test.gateway.filters.CorsFilter;
import com.test.gateway.filters.SwaggerHeaderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.test"})
@ComponentScan(basePackages = {"com.test"})
@ConfigurationPropertiesScan(basePackages = "com.test")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Component
    @Primary
    class DocumentationConfig implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            List resources = new ArrayList<>();
            // 服务名称/v2/api-docs
            resources.add(swaggerResource("用户服务api", "/system-module/v2/api-docs", "2.0"));
            resources.add(swaggerResource("权限服务api", "/auth-server/v2/api-docs","2.0"));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }
    }

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter();
    }

    @Bean
    public AuthGatewayFilter authGatewayFilter(){
        return new AuthGatewayFilter();
    }

    @Bean
    public SwaggerHeaderFilter swaggerHeaderFilter(){
        return new SwaggerHeaderFilter();
    }



}
