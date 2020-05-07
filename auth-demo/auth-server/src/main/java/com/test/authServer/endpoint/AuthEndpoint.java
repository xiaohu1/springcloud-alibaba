package com.test.authServer.endpoint;

import com.test.authServer.service.AuthenticationService;
import com.test.common.base.util.RedisUtils;
import com.test.common.base.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liujian
 * @ProjectName springcloud
 * @Description: 退出登录
 */
@RestController
public class AuthEndpoint {
	private static final String BEARER = "bearer";
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private RedisUtils redisUtils;



	/**
	 * 鉴权
	 * @param url
	 * @param method
	 * @param request
	 * @return
	 */
	@PostMapping("/auth/permission")
	public boolean decide(@RequestParam("url") String url, @RequestParam("method") String method,
								  HttpServletRequest request) {
		return authenticationService.decide(new HttpServletRequestAuthWrapper(request, url, method));
	}


	/**
	 * 退出token
	 *
	 * @param authHeader Authorization
	 */
	@GetMapping("/removeToken")
	public Result logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
		// 1.清空oauth2 token ,此方法 access_token 存储到数据库 可处理
//		if (StringUtils.hasText(authHeader)) {
//			String tokenValue = authHeader.replace(BEARER.toLowerCase(), "").trim();
//			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
//			if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
//				return  new Result("退出失败，token 为空");
//			}
//			tokenStore.removeAccessToken(accessToken);
//		}
		//2. 清空redis  token  ，此方法适用jwt ,redis 存储token
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username =  String.valueOf(authentication.getPrincipal());
		redisUtils.delete(username);
		return new Result();
	}
}
