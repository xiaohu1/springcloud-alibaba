package com.test.gateway.filters;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.test.authclient.service.AuthService;
import com.test.common.base.configuration.IgnoreUrlConfig;
import com.test.common.base.exception.RRException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

/**
 * 请求url权限校验
 */

@ComponentScan(basePackages = "com.test.authclient")
@Slf4j
@AllArgsConstructor
@Configuration
public class AccessGatewayFilter implements GlobalFilter {

    private static final String BEARER = "Bearer";
    private static final String BASIC = "Basic";
    private final AuthService authService;
    private final IgnoreUrlConfig ignoreUrlConfig;


    public boolean passUrl(String url) {
        return ignoreUrlConfig.getUrls().stream().anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    /**
     * 1.首先网关检查token是否有效，无效直接返回401，不调用签权服务
     * 2.调用签权服务器看是否对该请求有权限，有权限进入下一个filter，没有权限返回401
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        log.debug("url:{},method:{},headers:{}", url, method, request.getHeaders());
        // 白名单放行
        if (passUrl(url)) {
            return chain.filter(exchange);
        }
        // 如果请求未携带token信息, 直接跳出
        if (StringUtils.isBlank(authentication)) {
            log.debug("url:{},method:{},headers:{}, 请求未携带token信息", url, method, request.getHeaders());
            return unauthorized(exchange);
        }
        // 获取access_token 放掉
        if (authentication.startsWith(BASIC.toLowerCase())) {
            URI uri = exchange.getRequest().getURI();
            String queryParam = uri.getRawQuery();
            Map<String, String> paramMap = HttpUtil.decodeParamMap(queryParam, CharsetUtil.UTF_8);
//            paramMap.put("grant_type", "password");//密码账号获取access_token
            URI newUri = UriComponentsBuilder.fromUri(uri)
                    .replaceQuery(HttpUtil.toParams(paramMap))
                    .build(true)
                    .toUri();

            ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(newUri).build();
            return chain.filter(exchange.mutate().request(newRequest).build());

            //设置 headers Authorization 值
//            ServerHttpRequest.Builder builder = newRequest.mutate();
////            builder.header(HttpHeaders.AUTHORIZATION, OauthUtils.authHeaders());
//            return chain.filter(exchange.mutate().request(builder.build()).build());
        }
        if (authentication.startsWith(BEARER.toLowerCase())) {
            //1.验证token是否有效
            //2.验证url是否有请求权限
            int flag = authService.checkTokenInOauth2Client(authentication.substring(7), url);
            if (flag == 1) {
                throw new RRException("token 已失效，请从新登录", HttpStatus.UNAUTHORIZED.value());
            }
        }
        return chain.filter(exchange);
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}
