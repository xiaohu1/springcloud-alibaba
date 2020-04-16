package com.test.rocketmq.produce.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author liujian
 * @ProjectName springcloud-alibaba
 * @Description: TransactionListenerService
 * @date 2020/4/15  11:17
 */
@RocketMQTransactionListener(txProducerGroup = "myTxProducerGroup", corePoolSize = 5, maximumPoolSize = 10)
@Slf4j
public class TransactionListenerService implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        Object num = message.getHeaders().get("test");// 获取设定的指定key 是否存在并且正确
        if ("1".equals(num)) {
            log.info("executer: " + new String((byte[]) message.getPayload()) + "unknown");
            return RocketMQLocalTransactionState.UNKNOWN;
        } else if ("2".equals(num)) {
            log.info("executer: " + new String((byte[]) message.getPayload()) + "rollback");
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            log.info("executer: " + new String((byte[]) message.getPayload()) + "commit");
            return RocketMQLocalTransactionState.COMMIT;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("check: " + new String((byte[]) message.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }
}
