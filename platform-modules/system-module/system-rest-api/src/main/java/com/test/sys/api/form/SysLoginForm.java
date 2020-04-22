
package com.test.sys.api.form;

import lombok.Data;

/**
 * 登录表单
 *
 * @author liujian
 * @since 2.0.0 2018-10-25
 */
@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String randomStr;
    private boolean rememberMe;


}
