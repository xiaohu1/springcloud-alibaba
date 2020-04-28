package com.test.authclient.fegin;

import com.test.common.base.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

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
        boolean auth(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

        @Component
        class AuthClientFallbackImpl implements AuthClient {
            @Override
            public boolean auth(String authentication, String url, String method) {
                return false;
            }
        }
    }

