package com.test.sys.api.client;

import com.test.common.base.util.Result;
import com.test.sys.api.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



/**
 * @author liujian
 */
@FeignClient(name = "system-module",fallback = UserClient.UserServiceFallbackImpl.class)
public interface UserClient {

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping(value = "/rpc/user/getByName/{username}")
    Result<UserInfo> findUserByUsername(@PathVariable("username") String username);

    @Service
    @Slf4j
    class UserServiceFallbackImpl implements UserClient {

        @Override
        public Result<UserInfo> findUserByUsername(String username) {
            log.error("通过用户名查询用户异常:{}", username);
            return null;
        }
    }



}
