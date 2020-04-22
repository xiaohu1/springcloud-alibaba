package com.test.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.common.base.constant.Constant;
import com.test.sys.mapper.SysDeptMapper;
import com.test.sys.service.SysDeptService;
import com.test.sys.api.entity.SysDeptEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements SysDeptService {

    @Override
    public List<SysDeptEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysDeptEntity> deptList = this.list(new QueryWrapper<SysDeptEntity>().eq("parent_id", parentId));
        if (deptList == null) {
            return deptList;
        }
        List<SysDeptEntity> userDeptList = new ArrayList<>();
        for (SysDeptEntity dept : deptList) {
            if (menuIdList.contains(dept.getDeptId())) {
                userDeptList.add(dept);
            }
        }
        return userDeptList;
    }

    @Override
    public List<SysDeptEntity> getDeptList(SysDeptEntity sysDeptEntity) {

        //部门列表
        List<Long> menuIdList = this.list().stream().map(SysDeptEntity::getDeptId).collect(Collectors.toList());
        return getAllDeptList(menuIdList);
    }

    /**
     * 获取所有部门列表
     */
    private List<SysDeptEntity> getAllDeptList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysDeptEntity> menuList = queryListParentId((long) 0, menuIdList);
        //递归获取子菜单
        getDeptTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysDeptEntity> getDeptTreeList(List<SysDeptEntity> menuList, List<Long> menuIdList) {
        List<SysDeptEntity> subDeptList = new ArrayList<>();
        for (SysDeptEntity entity : menuList) {
            //目录
            if (entity.getParentId() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getDeptTreeList(queryListParentId(entity.getDeptId(), menuIdList), menuIdList));
            }
            subDeptList.add(entity);
        }
        return subDeptList;
    }


}
