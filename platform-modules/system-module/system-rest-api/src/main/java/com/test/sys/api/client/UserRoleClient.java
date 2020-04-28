package com.test.sys.api.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liujian
 */
@FeignClient(name = "system-module", fallback = UserRoleClient.SysUserRoleClientFallback.class)
public interface UserRoleClient {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    @PostMapping("/rpc/role/queryMenuIdListByRoleIds")
    List<String> queryMenuIdListByRoleIds(@RequestParam("roleIds") List<Long> roleId);

    @Component
    @Slf4j
    class SysUserRoleClientFallback implements UserRoleClient {

        @Override
        public List<String> queryMenuIdListByRoleIds(List<Long> roleId) {
            log.error("调用服务: {}, 资源: {}异常", "system-module", "/rpc/user/role/queryMenuIdListByRoleIds");
            return null;
        }
    }
}
