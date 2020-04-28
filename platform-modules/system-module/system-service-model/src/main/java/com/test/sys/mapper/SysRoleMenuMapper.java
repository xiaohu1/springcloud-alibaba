
package com.test.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.sys.api.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author liu jian
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {

    List<String> queryMenuIdListByRoleIds(List<Long> roleIds);


}
