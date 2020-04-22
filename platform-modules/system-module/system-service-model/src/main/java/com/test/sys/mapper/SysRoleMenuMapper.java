
package com.test.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.sys.api.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应关系
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {


}
