package com.test.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.sys.api.entity.SysDeptEntity;

import java.util.List;

/**
 * 部门管理
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
public interface SysDeptService extends IService<SysDeptEntity> {

    /**
     * 获取部门列表（树）
     */
    List<SysDeptEntity> getDeptList(SysDeptEntity sysDeptEntity);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<SysDeptEntity> queryListParentId(Long parentId, List<Long> menuIdList);


}
