package com.cnrmall.springcloud.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;

/**
 *  RocketMQ Stream 消费者模式
 */
@Slf4j
@Service
@EnableBinding({Sink.class})
public class StreamMessageReceive {

    @Autowired
     private Sink sink;

     @StreamListener(Sink.INPUT)
     public void getListener(Message<String> message){
            String msg =  message.getPayload();
            log.info("StreamMessageReceive====>>>>{}",  msg );
     }

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        String msg =  message.getPayload();
        log.info("input====>>>>{}",  msg );
    }
}