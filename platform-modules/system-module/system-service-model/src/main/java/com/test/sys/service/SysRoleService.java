package com.test.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.sys.api.entity.SysRoleEntity;

import java.util.Map;

/**
 * 角色
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 角色简单分页查询
     *
     * @param params 角色
     * @return
     */
    IPage<SysRoleEntity> getSysRolePage(Map<String, Object> params);


}
