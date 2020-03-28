package com.test.seata.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.seata.user.entity.UserAccount;
import com.test.seata.user.mapper.UserAccountMapper;
import com.test.seata.user.service.IUserAccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2020-03-07
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements
        IUserAccountService {

}
