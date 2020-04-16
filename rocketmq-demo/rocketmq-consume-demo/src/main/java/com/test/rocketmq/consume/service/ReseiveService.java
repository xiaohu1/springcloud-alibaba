package com.test.rocketmq.consume.service;

import com.test.rocketmq.consume.MQConsumeApplication.MySink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author liujian
 * @ProjectName springcloud-alibaba
 * @Description: ReseiveService
 * @date 2020/4/15  13:16
 */
@Service
@Slf4j
public class ReseiveService {

    @Autowired
    private MySink mySink;

    /**
     * 消费方通过注解 @StreamListener 消费消息
     */
    @StreamListener("inputOne")
    public void receiveInputOne(String receiveMsg){
        log.info("inputOne msg: "+ receiveMsg);
    }

    @StreamListener("inputTwo")
    public void receiveTnputTwo(String receiveMsg){
        log.info("inputTwo receiveMsg: "+receiveMsg);
    }

    @StreamListener("inputThr")
     public void receiveInputThr(@Payload User user){
        log.info("inputThr receive :" + user);
    }

    @StreamListener("inputFour")
    public void receiveTransactionalMsg(String trasactionnalMsg){
        log.info("inputFour receiveMsg: "+trasactionnalMsg);
    }

//    public void receiveInputFive() throws InterruptedException {
//        mySink.inputFive().poll(m -> {
//            String payload = (String) m.getPayload();
//            log.info("pull msg: " + payload);
//        }, new ParameterizedTypeReference<String>() {
//        });
//        Thread.sleep(2000);// 测试演示使用
//    }
}
