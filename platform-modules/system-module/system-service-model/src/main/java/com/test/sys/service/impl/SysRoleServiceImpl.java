package com.test.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.common.base.util.Query;
import com.test.sys.mapper.SysRoleMapper;
import com.test.sys.service.SysRoleService;
import com.test.sys.api.entity.SysRoleEntity;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 角色
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    /**
     * 角色简单分页查询
     *
     * @param params 角色
     * @return
     */
    @Override
    public IPage<SysRoleEntity> getSysRolePage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        return this.page(
                new Query<SysRoleEntity>(params).getPage(),
                new QueryWrapper<SysRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
        );
    }

}
