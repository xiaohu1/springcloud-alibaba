package com.test.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.test.authclient.service.AuthService;
import com.test.common.base.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Component
@Slf4j
public class AuthGatewayFilter extends AbstractGatewayFilterFactory{

	private final static String X_CLIENT_TOKEN_USER = "x-client-token-user";

//    private final static String X_CLIENT_TOKEN = "x-client-token";

	@Autowired
	private AuthService authService;


	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			String method = request.getMethodValue();
			String url = request.getPath().value();
			log.debug("url:{},method:{},headers:{}", url, method, request.getHeaders());

			// 不需要网关签权的url
			if (authService.ignoreAuthentication(url)) {
				return chain.filter(exchange);
			}

			if (StringUtils.isBlank(authentication)) {
				return unauthorized(exchange, Result.failed(401,"沒有权限"));
			}

			// 调用签权服务看用户是否有权限，若有权限进入下一个filter
			if (authService.hasPermission(authentication, url, method)) {
				ServerHttpRequest.Builder builder = request.mutate();
//				builder.header(X_CLIENT_TOKEN, "TODO 添加服务间简单认证");
				// 将jwt token中的用户信息传给服务
				String encodeStr = null;
				try {
					encodeStr = URLEncoder.encode(authService.getJwt(authentication).getClaims(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					log.error("Authentication encode exception.");
				}
				builder.header(X_CLIENT_TOKEN_USER, new String[] {encodeStr});
				//builder.header(X_CLIENT_TOKEN_USER, encodeStr);
				return chain.filter(exchange.mutate().request(builder.build()).build());
			}
			return unauthorized(exchange, Result.failed(401,"沒有权限"));
		};
	}

	/**
	 * 网关拒绝，返回401
	 *
	 * @param
	 */
	private Mono<Void> unauthorized(ServerWebExchange serverWebExchange,
                                    Result<Map<String, Object>> retMap) {
		serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		DataBuffer buffer = serverWebExchange.getResponse().bufferFactory()
				.wrap(JSONObject.toJSONString(retMap).getBytes());
		return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
	}


}
