package com.test.sentinelfeignConsumer.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sentinel-feign-demo",fallback = DemoFeignClient.FallbackDemoFeignClient.class)
public interface DemoFeignClient {

    @GetMapping("/feignTest")
    String sentinelFeignDemo();

    @Component
    @Slf4j
    class FallbackDemoFeignClient implements DemoFeignClient{

        public String sentinelFeignDemo() {
            log.info(" sentinel feign error");
            return null;
        }
    }
}
