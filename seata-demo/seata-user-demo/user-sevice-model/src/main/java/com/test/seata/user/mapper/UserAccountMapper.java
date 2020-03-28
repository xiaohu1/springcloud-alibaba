package com.test.seata.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.seata.user.entity.UserAccount;
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
public interface UserAccountMapper extends BaseMapper<UserAccount> {

}
