package com.test.authclient.service.impl;

import com.test.authclient.fegin.AuthClient;
import com.test.authclient.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author liujian
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    @Override
    public int checkTokenInOauth2Client(String tokenValue,String perms) {
     return authClient.checkTokenInOauth2Client(tokenValue,perms);
    }

}
