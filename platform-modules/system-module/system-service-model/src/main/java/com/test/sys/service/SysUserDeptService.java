package com.test.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.sys.api.entity.SysUserDeptEntity;

import java.util.List;

/**
 * 用户部门关系接口
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
public interface SysUserDeptService extends IService<SysUserDeptEntity> {

    /**
     * 根据用户ID，获取deptID列表
     */
    List<Long> queryDeptIdList(Long userId);

    void saveOrUpdate(Long userId, List<Long> deptIdList);

}
