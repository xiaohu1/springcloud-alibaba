package com.test.authServer.service.impl;

import com.google.common.collect.Sets;
import com.test.authServer.service.AuthenticationService;
import com.test.common.base.configuration.IgnoreUrlConfig;
import com.test.sys.api.client.UserRoleClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * 未在资源库中的URL默认标识
     */
    public static final String NONEXISTENT_URL = "NONEXISTENT_URL";

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private UserRoleClient userRoleClient;
    @Autowired
    private IgnoreUrlConfig ignoreUrlConfig;


    /**
     * @param request
     * @return 有权限true, 无权限false
     */
    @Override
    public boolean decide(HttpServletRequest request) {
        String requestUrl = request.getServletPath();
        String method = request.getMethod();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("用户:{}, 正在访问的url是:{}, method:{}", authentication.getPrincipal(), requestUrl, method);

        // 超级管理员不做权限验证
        String username = String.valueOf(authentication.getPrincipal());
        if(username.trim().toLowerCase().equals("admin")){
            return true;
        }
        Collection<? extends GrantedAuthority> authorityRoles = authentication.getAuthorities(); // 用户角色列表
        // 判断用户是否有权限
        Set<String> userResources = this.queryResourcesByAuthorityRoles(authorityRoles, authentication.getPrincipal());
        if (CollectionUtils.isEmpty(userResources)) {
            log.info("当前用户未配置资源访问权限, Principal: {}", authentication.getPrincipal());
            return false;
        }
        return isMatch(userResources, requestUrl, method);
    }

    /**
     * 查询角色资源列表
     *
     * @param authorityRoles
     * @param username
     * @return
     */
    private Set<String> queryResourcesByAuthorityRoles(Collection<? extends GrantedAuthority> authorityRoles, Object username) {
        log.info("用户被授予角色集合是：{}", authorityRoles);
        List<String> authorityRoleIds = authorityRoles.stream()
        		.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(authorityRoles))
            return null;
        
        // 转成long类型集合
        List<Long> roleIds = authorityRoleIds.stream().map(s -> Long.valueOf(s)).collect(Collectors.toList()) ;
        // 查询资源列表
        List<String> resources = userRoleClient.queryMenuIdListByRoleIds(roleIds);
        if(CollectionUtils.isEmpty(resources)) {
        	log.info("用户授权资源为空，用户：{}", authorityRoles);
        	return null;
        }
        log.info("用户授权资源列表：" + resources);
        
        // 转成set资源
        Set<String> resourseSet = Sets.newLinkedHashSet();
        for (String resourse : resources) {
        	if (StringUtils.isBlank(resourse))
        		continue;
        	String[] resArray = resourse.split(",");
        	resourseSet.addAll(Arrays.asList(resArray));
        }
        
        // 添加配置文件过滤url
        resourseSet.addAll(ignoreUrlConfig.getUrls());
        log.info("用户被授予角色的资源数量是:{}, 资源集合信息为:{}", resourseSet.size(), resourseSet);
        
        return resourseSet;
    }

    /**
     * 判断是否有资源访问权限
     *
     * @param userResources
     * @param url
     * @param method
     * @return
     */
    public boolean isMatch(Set<String> userResources, String url, String method) {
        boolean hasPermission = false;
        for (String menuUrl : userResources) {
            if (antPathMatcher.match(StringUtils.trim(menuUrl), url)) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }
}
