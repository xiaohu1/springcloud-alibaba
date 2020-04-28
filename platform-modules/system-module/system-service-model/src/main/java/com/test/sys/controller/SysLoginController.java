package com.test.sys.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import com.test.authclient.fegin.AuthClient;
import com.test.common.base.constant.Constant;
import com.test.common.base.po.User;
import com.test.common.base.util.Result;
import com.test.sys.api.form.SysLoginForm;
import com.test.sys.config.LoginConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.test.sys.config.LoginConfig.AUTH_PREFIX;

/**
 * @author liujian
 * @ProjectName springcloud-alibaba
 * @Description: SysLoginController
 * @date 2020/4/28  15:09
 */
@RestController
@Slf4j
public class SysLoginController {

    @Autowired
    private Producer producer;
    @Autowired
//    private RedisUtils redisUtils;
    private RedisTemplate redisTemplate;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private LoginConfig loginConfig;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public void captcha(@RequestParam String randomStr, HttpServletResponse response) {
        //生成验证码
        String text = producer.createText();
        //保存验证码信息
        redisTemplate.opsForValue().set(Constant.CODE_KEY + randomStr, text, 60); // 1分钟有效

        try {
            OutputStream out = response.getOutputStream();
            BufferedImage image = producer.createImage(text);
            ImageIO.write(image, "jpg", out);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("生成验证码异常:", e);
        }

    }

    /**
     * 登录
     *
     * @param loginForm
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody SysLoginForm loginForm) {
        // 1.判断验证码是否正确
        if (!validate(loginForm.getCaptcha(), loginForm.getRandomStr())) {
            return Result.failed("验证码不正确");
        }

         String grant_type = "password";// 密码方式获取token
        // 2.业务请求
        Map<String, Object> dataMap = authClient.oauth2(authHeaders(),grant_type,loginForm.getUsername(), loginForm.getPassword());

        // 登录成功
        if (dataMap.get("access_token") != null) {
            User user = JSON.parseObject(JSON.toJSONString(dataMap.get("userInfo")), User.class);
            // 把当前用户token 放到redis
            redisTemplate.opsForValue().set(user.getUsername(), dataMap.get("access_token"), 7 * 24 * 3600); // 7天有效
        }
        return Result.ok(dataMap);
    }


    /**
     * 校验验证码
     *
     * @param captcha
     * @param randomStr
     * @return
     */
    private boolean validate(String captcha, String randomStr) {
        if (StringUtils.isBlank(captcha)) {
            return false;
        }

        // 验证验证码是否正确
        String key = Constant.CODE_KEY + randomStr;
        Object validCode = redisTemplate.opsForValue().get(key);
        if (validCode == null || !captcha.equalsIgnoreCase(validCode.toString())) {
            return false;
        }
        // 删除验证码
        redisTemplate.delete(key);
        return true;
    }

    /**
     * 生成请求头 header
     * @return
     */
    private String authHeaders(){
        String base64Source =  loginConfig.getClientId()+ ":" + loginConfig.getClientSecret();
        return AUTH_PREFIX + Base64.encodeBase64String(base64Source.getBytes());
    }


}
