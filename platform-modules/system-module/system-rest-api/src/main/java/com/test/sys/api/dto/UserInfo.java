
package com.test.sys.api.dto;

import com.test.sys.api.entity.SysUserEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liujian
 * <p>
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 用户基本信息
     */
    private SysUserEntity sysUser;
    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Long[] roles;
}
