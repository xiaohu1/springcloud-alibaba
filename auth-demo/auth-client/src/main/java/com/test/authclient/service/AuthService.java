package com.test.authclient.service;

/**
 * @author liujian
 */

public interface AuthService {

    /**
     * 验证token
     * @param tokenValue
     */
     int checkTokenInOauth2Client(String tokenValue, String perms);


}
