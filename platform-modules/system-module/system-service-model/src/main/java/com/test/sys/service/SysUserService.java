package com.test.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.sys.api.entity.SysUserEntity;

import java.util.Map;

/**
 * 系统用户
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 系统用户简单分页查询
     *
     * @param params 系统用户
     * @return
     */
    IPage<SysUserEntity> getSysUserPage(Map<String, Object> params);


}
