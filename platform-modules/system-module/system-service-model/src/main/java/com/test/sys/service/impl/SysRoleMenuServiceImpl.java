package com.test.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.sys.mapper.SysRoleMenuMapper;
import com.test.sys.service.SysRoleMenuService;
import com.test.sys.api.entity.SysRoleMenuEntity;
import org.springframework.stereotype.Service;

/**
 * 角色与菜单对应关系
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {


}
