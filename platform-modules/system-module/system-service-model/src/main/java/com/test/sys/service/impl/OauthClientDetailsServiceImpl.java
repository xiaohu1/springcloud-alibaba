package com.test.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.sys.mapper.OauthClientDetailsMapper;
import com.test.sys.service.OauthClientDetailsService;
import com.test.sys.api.entity.OauthClientDetailsEntity;
import org.springframework.stereotype.Service;

/**
 * @author liu jian
 * @date 2019-06-26 21:03:21
 */
@Service("oauthClientDetailsService")
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetailsEntity> implements OauthClientDetailsService {


}
