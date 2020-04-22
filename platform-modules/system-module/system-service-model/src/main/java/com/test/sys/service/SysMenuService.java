package com.test.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.sys.api.entity.SysMenuEntity;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author liu jian
 * @date 2019-06-26 21:03:21
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 获取用户菜单列表
     */
    List<SysMenuEntity> getUserMenuList(Long userId);

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(Long userId);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

}
