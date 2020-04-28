package com.test.sys.rpc;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.common.base.exception.RRException;
import com.test.common.base.util.Result;
import com.test.sys.api.dto.UserInfo;
import com.test.sys.api.entity.SysUserEntity;
import com.test.sys.service.SysMenuService;
import com.test.sys.service.SysUserRoleService;
import com.test.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author liujian
 */
@RestController
@RequestMapping("/rpc/user")
public class SysUserRpc {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/getByName/{username}")
    public Result<UserInfo> findUserByUsername(@PathVariable("username") String username) {
        UserInfo userInfo = new UserInfo();
        //获取当前用户
        SysUserEntity sysUserEntity = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("username", username));
        if (sysUserEntity == null) {
            throw new RRException("当前用户为空");
        }
        //获取用户权限
        Set<String> permiss = sysMenuService.getUserPermissions(sysUserEntity.getUserId());
        //获取当前角色list
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(sysUserEntity.getUserId());
        userInfo.setSysUser(sysUserEntity);
        if(!CollectionUtils.isEmpty(permiss)){
            userInfo.setPermissions(ArrayUtil.toArray(permiss, String.class));
        }
        if(!CollectionUtils.isEmpty(roleIdList)){
            userInfo.setRoles(ArrayUtil.toArray(roleIdList, Long.class));
        }
        return Result.ok(userInfo);
    }




}
