package com.test.gateway.filters;

import cn.hutool.core.util.StrUtil;
import com.test.common.base.constant.Constant;
import com.test.common.base.constant.SecurityConstants;
import com.test.common.base.exception.RRException;
import com.test.common.base.util.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.common.base.util.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author liujian
 * 验证码处理
 */
@Slf4j
@Component
public class CodeGatewayFilter extends AbstractGatewayFilterFactory {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
//    private RedisUtils redisUtils;
    private RedisTemplate redisTemplate;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 不是登录请求，直接向下执行
            if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath()
                    , SecurityConstants.OAUTH_TOKEN_URL)) {
                return chain.filter(exchange);
            }

            // 刷新token，直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
                return chain.filter(exchange);
            }

            try {
                //校验验证码
                checkCode(request);
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                try {
                    return response.writeWith(Mono.just(response.bufferFactory()
                            .wrap(objectMapper.writeValueAsBytes(
                                    R.error(e.getMessage())))));
                } catch (JsonProcessingException e1) {
                    log.error("对象输出异常", e1);
                }
            }

            return chain.filter(exchange);
        };
    }

    /**
     * 检查code
     *
     * @param request
     */
    @SneakyThrows
    private void checkCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StrUtil.isBlank(code)) {
            throw new RRException("验证码不能为空");
        }
        String randomStr = request.getQueryParams().getFirst("randomStr");
        if (StrUtil.isBlank(randomStr)) {
            randomStr = request.getQueryParams().getFirst("mobile");
        }
        String key = Constant.CODE_KEY + randomStr;
        Object saveCode = redisTemplate.opsForValue().get(key);
        if (saveCode == null || StrUtil.isBlank(saveCode.toString()) || !StrUtil.equals(saveCode.toString(), code)) {
            redisTemplate.delete(key);
            throw new RRException("验证码不合法");
        }
        redisTemplate.delete(key);
    }
}
