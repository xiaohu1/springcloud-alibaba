package com.test.authServer.endpoint;

import cn.hutool.core.util.StrUtil;
import com.test.common.base.util.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujian
 * @ProjectName springcloud
 * @Description: 退出登录
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tokenEndpoint")
public class RemoveToken {
	private final TokenStore tokenStore;
    private static final String BEARER = "Bearer";

	/**
	 * 退出token
	 *
	 * @param authHeader Authorization
	 */
	@GetMapping("/removeToken")
	public Result logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
		if (StringUtils.hasText(authHeader)) {
			String tokenValue = authHeader.replace(BEARER.toLowerCase(), "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
				return  new Result("退出失败，token 为空");
			}
			tokenStore.removeAccessToken(accessToken);
		}

		return new Result();
	}
}
