package com.test.rocketmq.produce.service;

import com.test.rocketmq.produce.MQProduceApplication.MySource;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liujian
 * @ProjectName springcloud-alibaba
 * @Description: SenderService
 * @date 2020/4/15  10:48
 */
@Service
public class SenderService {

    @Autowired
    private MySource mySource;

    public void send(String msg) {
        mySource.outputOne().send(MessageBuilder.withPayload(msg).build());
    }

    public <T> void sendWithTags(T msg, String tag) {
        Message<T> message = MessageBuilder.createMessage(msg, new MessageHeaders(Stream.of(tag)
                .collect(Collectors.toMap(str -> MessageConst.PROPERTY_TAGS, String::toString))));
        mySource.outputOne().send(message);
    }

    public <T> void sendObject(T msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        mySource.outputOne().send(message);
    }

    public <T> void sendTransactionalMsg(T msg, int num) {
        MessageBuilder builder = MessageBuilder.withPayload(msg)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        builder.setHeader("test", String.valueOf(num));
        builder.setHeader(RocketMQHeaders.TAGS, "binder");
        Message message = builder.build();
        mySource.outputTwo().send(message);
    }

    public void sendOutputThr(String msg) {
        mySource.outputThr().send(MessageBuilder.withPayload(msg).build());
    }

}
