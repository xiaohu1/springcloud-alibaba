

package com.test.common.base.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

/**
 * @author liujian
 * @date 2019/3/18
 * 扩展用户信息
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    /**
     * 人相关信息
     */
    private Long userId; // 用户ID
    private String username; // 用户名
    private String mobile; // 手机号
    private String realname; // 真实姓名
    private String roleNames;

    /**
     * 数据权限相关信息
     */
    private Set<Long> roleIds; // 角色集合
    private Set<String> roleEngLishNames; // 角色英文名称集合


    // 后期删掉
    private Long deptId;

}
