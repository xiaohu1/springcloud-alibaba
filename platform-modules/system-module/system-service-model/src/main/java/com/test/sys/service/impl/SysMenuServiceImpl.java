package com.test.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.common.base.constant.Constant;
import com.test.sys.mapper.SysMenuMapper;
import com.test.sys.mapper.SysUserRoleMapper;
import com.test.sys.service.SysMenuService;
import com.test.sys.api.entity.SysMenuEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 菜单管理
 *
 * @author liu jian
 * @date 2019-06-26 21:03:21
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = this.list(new QueryWrapper<SysMenuEntity>().eq("parent_id", parentId));
        if (menuIdList == null) {
            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if (userId.equals(Constant.SUPER_ADMIN)) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserRoleMapper.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId.equals(Constant.SUPER_ADMIN)) {
            List<SysMenuEntity> menuList = list();
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getUrl());
            }
        } else {
            permsList = sysUserRoleMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId((long) 0, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

}
