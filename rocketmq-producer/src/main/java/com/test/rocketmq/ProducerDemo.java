package com.test.rocketmq;

import com.google.common.collect.Maps;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

public class ProducerDemo implements CommandLineRunner {


    @Output("output")
    private MessageChannel output;

    public void run(String... args) throws Exception {
        Map<String,Object> headers = Maps.newHashMap();
        headers.put(MessageConst.PROPERTY_TAGS,"tagStr");
        Message<String> message  = MessageBuilder.createMessage("hello producer",new MessageHeaders(headers));
//        output.send(MessageBuilder.withPayload("hello producer").build());
        output.send(message);
    }
}
