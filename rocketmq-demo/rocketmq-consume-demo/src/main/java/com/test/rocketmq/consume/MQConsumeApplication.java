package com.test.rocketmq.consume;

import com.test.rocketmq.consume.MQConsumeApplication.MySink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author liujian
 * @ProjectName springcloud-alibaba
 * @Description: MQConsumeApplication
 * @date 2020/4/14  13:37
 */
@SpringBootApplication
@EnableBinding({MySink.class})
public class MQConsumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MQConsumeApplication.class, args);
    }

    public interface MySink {
        @Input("inputOne")
        SubscribableChannel inputOne();

        @Input("inputTwo")
        SubscribableChannel inputTwo();

        @Input("inputThr")
        SubscribableChannel inputThr();

        @Input("inputFour")
        SubscribableChannel inputFour();

        @Input("inputFive")
        PollableMessageSource inputFive();
    }

    @Bean
    ConsumerCustomRunner consumerCustomRunner(){
        return new ConsumerCustomRunner();
    }
    @Slf4j
    public static class ConsumerCustomRunner implements CommandLineRunner {

        @Autowired
        private MySink mySink;

        @Override
        public void run(String... args) throws Exception {
            while (true) {
                mySink.inputFive().poll(m -> {
                    String payload = (String) m.getPayload();
                    log.info("pull msg: " + payload);
                }, new ParameterizedTypeReference<String>() {
                });
                Thread.sleep(2000);// 测试演示使用
            }
        }
    }


}
