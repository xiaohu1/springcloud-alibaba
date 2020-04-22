package com.test.sys.feign;

import com.test.common.base.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tiger-order", fallback = UserOrder.UserOrderFallback.class)
public interface UserOrder {

    @GetMapping("/api/order/user/{userId}")
    Result queryOrderByUserId(@PathVariable("userId") Long userId);

    @Slf4j
    @Component
     class UserOrderFallback implements UserOrder {

        @Override
        public Result queryOrderByUserId(Long userId) {
            log.info("获取用户订单失败", userId);
            return null;
        }
    }
}
