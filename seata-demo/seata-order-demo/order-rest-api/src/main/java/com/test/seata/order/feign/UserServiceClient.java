package com.test.seata.order.feign;


import com.test.seata.commons.DefaultResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 描述: author: liujian date: 2020-03-07
 */

@FeignClient(name = "seata-user-demo",fallback = UserServiceClient.FallbackUserServiceClient.class)
public interface UserServiceClient {

  /**
   * 扣除用户余额
   * @param userId
   * @param money
   * @return
   */
  @PostMapping(value = "/api/v1/user/account/deduction")
  DefaultResult deduction(@RequestParam("userId") String userId, @RequestParam("money") Integer money);

  @Component
  @Slf4j
  class FallbackUserServiceClient implements UserServiceClient{

    public DefaultResult deduction(String userId, Integer money) {
      log.error("扣除用户余额接口服务降级 url: /api/v1/user/account/deduction");
      return null;
    }
  }
}
