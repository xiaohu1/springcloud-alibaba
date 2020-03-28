package com.test.seata.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.seata.order.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2020-03-07
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
