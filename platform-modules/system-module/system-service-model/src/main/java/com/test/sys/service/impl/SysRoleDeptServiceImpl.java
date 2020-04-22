package com.test.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.sys.mapper.SysRoleDeptMapper;
import com.test.sys.service.SysRoleDeptService;
import com.test.sys.api.entity.SysRoleDeptEntity;
import org.springframework.stereotype.Service;

/**
 * 角色与部门对应关系
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDeptEntity> implements SysRoleDeptService {


}
