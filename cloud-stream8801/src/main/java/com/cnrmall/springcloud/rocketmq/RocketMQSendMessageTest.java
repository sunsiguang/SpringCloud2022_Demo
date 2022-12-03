package com.cnrmall.springcloud.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

@Slf4j
public class RocketMQSendMessageTest {

    public static void main(String[] args)  throws Exception{

        DefaultMQProducer producer = new DefaultMQProducer("myproducer-group");

        producer.setNamesrvAddr("192.168.7.16:9876");

        producer.start();

        Message message = new Message("myTopic","myTags","Test RocketMQ Message".getBytes()) ;

        SendResult  result = producer.send(message, 10000);

        log.info( "====result===={}" ,result);

        producer.shutdown();
    }
}