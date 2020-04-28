package com.test.authclient.service;

import org.springframework.security.jwt.Jwt;

/**
 * @author liujian
 */

public interface AuthService {


    /**
     *
     * @param url  白名单放行
     * @return
     */
    boolean ignoreAuthentication(String url);

    /**
     * 调用签权服务，判断用户是否有权限
     * @param authentication
     * @param url
     * @param method
     * @return true/false
     */
    boolean hasPermission(String authentication, String url, String method);

    /**
     * 从认证信息中提取jwt token 对象
     *
     * @param authentication 认证信息  Authorization: bearer header.payload.signature
     * @return Jwt对象
     */
    Jwt getJwt(String authentication);

}
