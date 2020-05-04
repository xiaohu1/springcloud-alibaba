package com.test.authclient.fegin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author liujian
 */
@FeignClient(name = "auth-server",fallback = AuthClient.AuthClientFallbackImpl.class)
public interface AuthClient{

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return
     */
    @PostMapping(value = "/auth/permission")
    boolean auth(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam(name = "url") String url, @RequestParam(name = "method") String method);

    /**
     *  oauth2 获取 access_token 信息
     */
    @PostMapping(value = "/oauth/token")
    Map<String, Object> oauth2(@RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization,
                               @RequestParam(name = "grant_type") String grant_type,
                               @RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password);

    @Component
    @Slf4j
    class AuthClientFallbackImpl implements AuthClient {
        @Override
        public boolean auth(String authentication, String url, String method) {
            log.info("/auth/permission ：服务降级");
            return false;
        }

        @Override
        public Map<String, Object> oauth2(String Authorization, String grant_type, String username, String password) {
                log.info("/oauth/token : 服务降级");
               return null;
        }
    }
}

