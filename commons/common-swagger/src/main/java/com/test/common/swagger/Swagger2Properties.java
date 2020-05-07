package com.test.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * swagger2 配置
 * @author liujian
 **/
@Data
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2Properties {

    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String apiName;
    private String apiKeyName;
    private String termsOfServiceUrl;

}
