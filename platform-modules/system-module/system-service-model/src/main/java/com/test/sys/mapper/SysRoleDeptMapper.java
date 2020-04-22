
package com.test.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.sys.api.entity.SysRoleDeptEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与部门对应关系
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Mapper
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDeptEntity> {


}
