package com.test.authclient.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.test.authclient.fegin.AuthClient;
import com.test.authclient.service.AuthService;
import com.test.common.base.configuration.IgnoreUrlConfig;
import com.test.common.base.util.RedisUtils;
import com.test.common.base.util.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author liujian
 */
@Service
@RefreshScope
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthClient authClient;
    @Autowired
    private IgnoreUrlConfig ignoreUrlConfig;
    @Autowired
//    private RedisUtils redisUtils;
    private RedisTemplate redisTemplate;


    /**
     * jwt token 密钥，主要用于token解析，签名验证
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    /**
     * jwt验签
     */
    private MacSigner verifier;


    @Override
    public boolean ignoreAuthentication(String url) {
        if (StrUtil.isBlank(url)) {
            return false;
        }
        return ignoreUrlConfig.getUrls().stream().anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    @Override
    public boolean hasPermission(String authentication, String url, String method) {
        // token是否有效，JWT校验
        if (invalidJwtAccessToken(authentication)) {
            return Boolean.FALSE;
        }

        // Redis校验
        Jwt jwt = this.getJwt(authentication);
        String claims = jwt.getClaims();
        JSONObject jsonObject = JSONObject.parseObject(claims);
        String username = jsonObject.getString("username");
        Object object = redisTemplate.opsForValue().get(username);
        if (object == null) {
            log.error("用户名：{}，token失效过期。", username);
            return Boolean.FALSE;
        }
        String access_token = String.valueOf(object);
        if (StringUtils.isBlank(access_token)) {
            log.error("用户名：{}，token失效过期。", username);
            return Boolean.FALSE;
        }

        // 从认证服务获取是否有权限
        return authClient.auth(authentication, url, method);
    }

    @Override
    public Jwt getJwt(String authentication) {
        return JwtHelper.decode(StringUtils.substring(authentication, 7));
    }


    /**
     * 验证authentication是否有效， true-无效，false-有效
     *
     * @param authentication
     * @return
     */
    private boolean invalidJwtAccessToken(String authentication) {
        verifier = Optional.ofNullable(verifier).orElse(new MacSigner(signingKey));
        // 是否无效true表示无效
        boolean invalid = Boolean.TRUE;
        try {
            Jwt jwt = getJwt(authentication);
            jwt.verifySignature(verifier);
            invalid = Boolean.FALSE;

        } catch (InvalidSignatureException | IllegalArgumentException ex) {
            log.warn("user token has expired or signature error ");
        }
        return invalid;
    }
}
