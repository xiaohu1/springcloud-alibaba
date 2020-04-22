
package com.test.sys.api.form;

import com.test.common.base.util.validation.QueryGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 密码表单
 *
 * @author liujian
 * @since 1.4.0 2018-10-25
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    @NotNull(groups = {QueryGroup.class}, message = "原密码不能为空")
    private String password;
    /**
     * 新密码
     */
    @NotNull(groups = {QueryGroup.class}, message = "新密码不能为空")
    private String newPassword;

}
