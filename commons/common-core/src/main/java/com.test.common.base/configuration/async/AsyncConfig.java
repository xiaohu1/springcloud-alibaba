package com.test.common.base.configuration.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 * @author liujian
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

    @Resource
    private AsyncConstant asyncConstant;

    @Bean
    public Executor asyncServiceExecutor() {
        logger.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(asyncConstant.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(asyncConstant.getMaxPoolSize());
        //配置队列大小
        executor.setQueueCapacity(asyncConstant.getQueueCapacity());
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(asyncConstant.getNamePrefix());
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //对拒绝task的处理策略
        executor.setKeepAliveSeconds(asyncConstant.getKeepAlive());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
