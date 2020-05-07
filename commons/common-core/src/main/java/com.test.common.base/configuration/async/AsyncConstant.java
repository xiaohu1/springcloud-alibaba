package com.test.common.base.configuration.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: liujian
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "async")
public class AsyncConstant {

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 配置最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 队列大小
     */
    private Integer queueCapacity;

    /**
     *  线程的名称前缀
     */
    private String namePrefix;

    /**
     * 允许的空闲时间
     */
    private Integer keepAlive;

}
