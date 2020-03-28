package com.test.seata.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.seata.order.entity.OrderInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liujian
 * @since 2020-03-07
 */
public interface IOrderInfoService extends IService<OrderInfo> {

  /**
   * 生成用户订单
   * @param userId
   * @param commodityCode
   * @param count
   */
  void generateOrder(String userId, String commodityCode, Integer count);
}
