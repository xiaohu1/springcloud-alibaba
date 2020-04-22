
package com.test.sys.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户
 *
 * @author liu jian
 * @date 2019-06-26 18:55:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUserEntity extends Model<SysUserEntity> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     *
     */
    private String realname;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别 1:男,2:女,3:未知
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 0:管理员，1：教师
     */
    private Integer userType;

    /**
     * 部门列表
     */
    @TableField(exist = false)
    private List<SysDeptEntity> deptList;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<SysRoleEntity> roleList;

}
