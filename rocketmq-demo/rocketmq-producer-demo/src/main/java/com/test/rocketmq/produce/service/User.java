package com.test.rocketmq.produce.service;

import lombok.Data;

/**
 * @author liujian
 * @ProjectName springcloud-alibaba
 * @Description: User
 * @date 2020/4/15  10:58
 */
@Data
public class User {

    private int userId;
    private String username;

    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
