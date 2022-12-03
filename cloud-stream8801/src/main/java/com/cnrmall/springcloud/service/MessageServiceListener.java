package com.cnrmall.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 *  RocketMQ 原始消费模式
 */
@Slf4j
@Service
@RocketMQMessageListener( consumerGroup = "myConsumer-Group1",topic = "myTopic")
public class MessageServiceListener  implements RocketMQListener<String> {
    public void onMessage(String str) {
        log.info( "MessageServiceListener=====>>>>>>{}" ,  str );
    }
}