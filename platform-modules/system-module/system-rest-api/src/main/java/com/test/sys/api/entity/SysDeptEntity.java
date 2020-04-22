
package com.test.sys.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部门管理
 *
 * @author liu jian
 * @date 2019-06-26 18:55:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDeptEntity extends Model<SysDeptEntity> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long deptId;
    /**
     * 上级部门ID，一级部门为0
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 是否删除  0：已删除  1：正常
     */
    private Integer delFlag;
    /**
     * 行业类型
     */
    private Integer businessType;

    @TableField(exist = false)
    private List<?> list;

}
