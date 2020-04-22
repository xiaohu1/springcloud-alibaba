package com.test.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.common.base.util.MapUtils;
import com.test.sys.mapper.SysUserDeptMapper;
import com.test.sys.service.SysUserDeptService;
import com.test.sys.api.entity.SysUserDeptEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户部门关系接口
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Service("sysUserDeptService")
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDeptEntity> implements SysUserDeptService {


    @Override
    public List<Long> queryDeptIdList(Long userId) {
        return baseMapper.queryDeptIdList(userId);
    }

    @Override
    public void saveOrUpdate(Long userId, List<Long> deptIdList) {
        //先删除用户与部门关系
        this.removeByMap(new MapUtils().put("user_id", userId));

        if (CollectionUtils.isEmpty(deptIdList)) {
            return;
        }

        //保存用户与部门关系
        List<SysUserDeptEntity> list = new ArrayList<>(deptIdList.size());
        for (Long deptId : deptIdList) {
            SysUserDeptEntity sysUserDeptEntity = new SysUserDeptEntity();
            sysUserDeptEntity.setUserId(userId);
            sysUserDeptEntity.setDeptId(deptId);
            list.add(sysUserDeptEntity);
        }
        this.saveBatch(list);
    }


}
