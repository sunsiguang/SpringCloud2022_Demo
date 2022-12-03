package com.cnrmall.springcloud.mq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class RocketMQSendMessageTest {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("myproduct-group");
        producer.setNamesrvAddr("192.168.7.60:9876");

        producer.start();

        Message message = new Message("myTopic","myTag","Test RocketMQ Message".getBytes());

        SendResult result = producer.send(message);
        System.out.println(result);

        producer.shutdown();

    }
}