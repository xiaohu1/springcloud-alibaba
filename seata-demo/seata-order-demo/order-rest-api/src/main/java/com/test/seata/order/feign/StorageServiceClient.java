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

@FeignClient(name = "seata-storage-demo",fallback = StorageServiceClient.FallbackStorageServiceClient.class)
public interface StorageServiceClient {

  /**
   * 减去库存
   * @param commodityCode
   * @param count
   * @return
   */
  @PostMapping(value = "/api/v1/storage/deduction")
  DefaultResult deduction(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);

  @Component
  @Slf4j
  class FallbackStorageServiceClient implements StorageServiceClient{

    public DefaultResult deduction(String commodityCode, Integer count) {
      log.error("减去库存端口服务降级：/api/v1/storage/deduction");
      return null;
    }
  }


}
