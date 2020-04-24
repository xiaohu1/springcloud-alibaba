package com.test.authServer.client;

import com.test.authServer.feign.UserService;
import com.test.common.base.constant.SecurityConstants;
import com.test.common.base.exception.RRException;
import com.test.common.base.util.Result;
import com.test.sys.api.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author liujian
 * @ProjectName tiger-springcloud
 * @Description: AccessTokenService
 * @date 2019/7/9  11:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthTokenService {
    private final UserService remoteUserService;

    /**
     * 客户端申请access_token校验 , 验证url 权限(可以改成jwt 验证(这个类只是默认uuid生成密码，原生验证access_token 测试))
     *
     * @param tokenValue
     * @return
     */
    @GetMapping("/checkToken")
    public int checkTokenInOauth2Client(@RequestParam(value = "access_token") String tokenValue, @RequestParam(value = "perms",required = false) String perms) {
        int flag = 0;
        try {
            RestTemplate restTemplate = new RestTemplate();
            OAuth2AccessToken oauth2AccessToken = restTemplate.getForObject(
                    SecurityConstants.OAUTH_CHECK_TOKEN + "?token=" + tokenValue, OAuth2AccessToken.class);
            if (oauth2AccessToken == null) {
                //非法的Token值
                throw new RRException("非法的Token!", HttpStatus.UNAUTHORIZED.value());
            } else if (oauth2AccessToken.isExpired()) {
                //token失效
                throw new RRException("Token失效，请重新登录!", HttpStatus.UNAUTHORIZED.value());
            }
            // 验证url 权限    不建议，缓存问题要处理
//            ArrayList<String> authorities = (ArrayList<String>) oauth2AccessToken.getAdditionalInformation().get("authorities");
            //获取用户认证信息
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) oauth2AccessToken.getAdditionalInformation().get("user_name");
            Result<UserInfo> result = remoteUserService.findUserByUsername(username);
            //用户拥有权限资源 与 url要求的资源进行对比
            if (!isMatch(perms, Arrays.asList(result.getData().getPermissions()))) {
                throw new RRException("非法url 访问!", HttpStatus.UNAUTHORIZED.value());
            }
        } catch (Exception e) {
            log.error("checkTokenInOauth2Client failure:", e);
            flag = 1;
        }
        return flag;
    }

    /**
     * url对应资源与用户拥有资源进行匹配k
     *
     * @param perms
     * @param userResources
     * @return
     */
    public boolean isMatch(String perms, List<String> userResources) {
        return userResources.stream().anyMatch(url -> url.equals(perms));
    }

}
