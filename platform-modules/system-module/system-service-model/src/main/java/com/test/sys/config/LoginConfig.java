package com.test.sys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "login")
@Data
@RefreshScope
public class LoginConfig {
	
	public static final String AUTH_PREFIX = "Basic ";
	
	private String clientId;
	
	private String clientSecret;
	
}
