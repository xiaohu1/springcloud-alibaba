
package com.test.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.sys.api.entity.SysUserDeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户部门关系接口
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Mapper
public interface SysUserDeptMapper extends BaseMapper<SysUserDeptEntity> {

    /**
     * 根据用户ID，获取deptID列表
     */
    List<Long> queryDeptIdList(Long userId);


}
