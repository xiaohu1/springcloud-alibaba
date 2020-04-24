package com.test.authServer.webUrl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujian
 * @ProjectName tiger-springcloud
 * @Description: testPost
 * @date 2019/7/23  18:01
 */
@RestController
@RequestMapping("/testPost")
@RefreshScope
public class testPost {

    @Value("${ignore.urls}")
    private String pramsUrl;

    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    @GetMapping("/postMethod")
    public String postMethod(){
        System.out.println("111111111111111111"+pramsUrl);
        System.out.println("2222222222           :"+      signingKey);
        System.out.println("q123132123");
        return pramsUrl;
    }


}
