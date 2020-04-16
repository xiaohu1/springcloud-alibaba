package com.test.rocketmq.produce;

import com.test.rocketmq.produce.MQProduceApplication.MySource;
import com.test.rocketmq.produce.service.SenderService;
import com.test.rocketmq.produce.service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;

/**
 * @author liujian
 * @ProjectName springcloud-alibaba
 * @Description: MQProduceApplication
 * @date 2020/4/14  13:38
 */
@SpringBootApplication
@EnableBinding({MySource.class})
public class MQProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MQProduceApplication.class, args);
    }

    @Bean
    CustomRunner customRunnerOne() {
        return new CustomRunner("outputOne");
    }


    @Bean
    CustomRunner customRunnerThr() {
        return new CustomRunner("outputThr");
    }

    @Bean
    TrasactionnalCustomerRunner trasactionnalCustomerRunner(){
        return new TrasactionnalCustomerRunner();
    }

    public interface MySource {

        @Output("outputOne")
        MessageChannel outputOne();

        @Output("outputTwo")
        MessageChannel outputTwo();

        @Output("outputThr")
        MessageChannel outputThr();

    }

    public static class CustomRunner implements CommandLineRunner {

        private final String bindingName;

        public CustomRunner(String bindingName) {
            this.bindingName = bindingName;
        }

        @Autowired
        private SenderService senderService;


        @Override
        public void run(String... args) throws Exception {

            if (this.bindingName.equals("outputOne")) {
                int count = 5;
                for (int i = 0; i < count; i++) {
                    String msgContent = "msg-" + i;
                    if (i % 3 == 0) {
                        senderService.send(msgContent);
                    } else if (i % 3 == 1) {
                        senderService.sendWithTags(msgContent, "tagStr");
                    } else {
                        senderService.sendObject(new User(1, "xiaoliu"), "tagObj");
                    }
                }
            } else if (this.bindingName.equals("outputThr")) {
                int count = 20;
                for (int i = 0; i < count; i++) {
                    String msgContent = "pull-msg" + i;
                    senderService.sendOutputThr(msgContent);
                }
            }
        }
    }

    public static class TrasactionnalCustomerRunner implements CommandLineRunner {

        @Autowired
        private SenderService senderService;

        @Override
        public void run(String... args) throws Exception {
            //commit_message
            senderService.sendTransactionalMsg("trasactional-msg1", 1);
            senderService.sendTransactionalMsg("trasactional-msg4", 4);
            // rollback_message
            senderService.sendTransactionalMsg("trasactional-msg2", 2);
            senderService.sendTransactionalMsg("trasactional-msg3", 3);

        }
    }

}
