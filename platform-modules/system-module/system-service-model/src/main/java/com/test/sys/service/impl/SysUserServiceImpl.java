package com.test.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.common.base.util.Query;
import com.test.sys.mapper.SysUserMapper;
import com.test.sys.api.entity.SysDeptEntity;
import com.test.sys.api.entity.SysRoleEntity;
import com.test.sys.api.entity.SysUserEntity;
import com.test.sys.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserDeptService sysUserDeptService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 系统用户简单分页查询
     *
     * @param params 系统用户
     * @return
     */
    @Override
    public IPage<SysUserEntity> getSysUserPage(Map<String, Object> params) {
        IPage<SysUserEntity> page = this.page(new Query<SysUserEntity>(params).getPage(), new QueryWrapper<SysUserEntity>()
                .like(StringUtils.isNotBlank(String.valueOf(params.get("username")))
                        , "username", String.valueOf(params.get("username")))
                .eq(params.get("userId") != null, "user_id", params.get("userId"))
        );
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            for (SysUserEntity userEntity : page.getRecords()) {
                List<SysDeptEntity> deptList = new ArrayList<>();
                List<SysRoleEntity> roleList = new ArrayList<>();
                //1.获取部门名称
                List<Long> deptIds = sysUserDeptService.queryDeptIdList(userEntity.getUserId());
                if (!CollectionUtils.isEmpty(deptIds)) {
                    for (int i = 0; i < deptIds.size(); i++) {
                        SysDeptEntity deptEntity = sysDeptService.getById(deptIds.get(i));
                        if (deptEntity != null) {
                            deptList.add(deptEntity);
                        }
                    }
                    userEntity.setDeptList(deptList);
                }
                //2.获取角色名称
                List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userEntity.getUserId());
                if (!CollectionUtils.isEmpty(roleIdList)) {
                    for (int i = 0; i < roleIdList.size(); i++) {
                        SysRoleEntity roleEntity = sysRoleService.getById(roleIdList.get(i));
                        if (roleEntity != null) {
                            roleList.add(roleEntity);
                        }
                    }
                    userEntity.setRoleList(roleList);
                }
            }
        }
        return page;
    }

}
