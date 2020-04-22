package com.test.authclient.fegin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liujian
 */
@FeignClient(name = "auth-server",fallback = AuthClient.AuthClientFallbackImpl.class)
public interface AuthClient {

    /**
     *  客户端申请access_token校验,验证url 权限
     */
    @GetMapping("/api/auth/checkToken")
    int checkTokenInOauth2Client(@RequestParam("token") String tokenValue, @RequestParam("perms") String perms);

    @Component
    @Slf4j
    class AuthClientFallbackImpl implements AuthClient {

        @Override
        public int checkTokenInOauth2Client(String tokenValue, String perms) {
            log.info("客户端申请access_token校验 /api/auth/checkToken 服务降级处理");
            return 0;
        }
    }

}
