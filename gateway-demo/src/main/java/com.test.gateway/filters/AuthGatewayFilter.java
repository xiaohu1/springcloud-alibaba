package com.test.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.test.authclient.fegin.AuthClient;
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
public class AuthGatewayFilter extends AbstractGatewayFilterFactory {

    private final static String X_CLIENT_TOKEN_USER = "x-client-token-user";

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthClient authClient;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String method = request.getMethodValue();
            String url = request.getPath().value();
            // 不需要网关签权的url
            if (authService.ignoreAuthentication(url)) {
                return chain.filter(exchange);
            }
            Map<String, Object> retMap = Maps.newHashMap();
            if (StringUtils.isBlank(authentication)) {
                retMap.put(String.valueOf(HttpStatus.UNAUTHORIZED.value()),HttpStatus.UNAUTHORIZED.getReasonPhrase());
                return unauthorized(exchange, retMap);
            }

            // 调用签权服务看用户是否有权限，若有权限进入下一个filter
            Result<Boolean> flag = authClient.auth(authentication,url,method);
//            boolean flag = authService.hasPermission(authentication, url, method);
            if (flag.getData()) {
                ServerHttpRequest.Builder builder = request.mutate();
                // 将jwt token中的用户信息传给服务
                String encodeStr = null;
                try {
                    encodeStr = URLEncoder.encode(authService.getJwt(authentication).getClaims(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    log.error("Authentication encode exception.");
                }
                builder.header(X_CLIENT_TOKEN_USER, new String[]{encodeStr});
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
            retMap.put(String.valueOf(HttpStatus.UNAUTHORIZED.value()),HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return unauthorized(exchange, retMap);
        };
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange,
                                   Map<String, Object> retMap) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory()
                .wrap(JSONObject.toJSONString(retMap).getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


}
