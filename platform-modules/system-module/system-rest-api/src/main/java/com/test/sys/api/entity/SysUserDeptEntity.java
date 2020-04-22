
package com.test.sys.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liu jian
 * @date 2019-06-26 18:55:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_dept")
public class SysUserDeptEntity extends Model<SysUserDeptEntity> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 部门id
     */
    private Long deptId;

}
