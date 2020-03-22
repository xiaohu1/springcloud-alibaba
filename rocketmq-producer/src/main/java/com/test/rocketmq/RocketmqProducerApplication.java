package com.test.rocketmq;

import com.google.common.collect.Maps;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

@SpringBootApplication
@EnableBinding({Source.class})
public class RocketmqProducerApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(RocketmqProducerApplication.class, args);
    }


//    public static class ProducerDemo implements CommandLineRunner {

        @Autowired
        private MessageChannel output;

        public void run(String... args) throws Exception {
        Map<String,Object> headers = Maps.newHashMap();
        headers.put(MessageConst.PROPERTY_TAGS,"tagStr");
        Message message  = MessageBuilder.createMessage("hello producer test",new MessageHeaders(headers));
        output.send(message);
//            output.send(MessageBuilder.withPayload("hello producer").build());
//            System.out.println("hello producer");
        }
//    }

}
