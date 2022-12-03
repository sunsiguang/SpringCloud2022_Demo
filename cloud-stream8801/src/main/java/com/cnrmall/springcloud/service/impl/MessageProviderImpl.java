package com.cnrmall.springcloud.service.impl;

import com.cnrmall.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 定义消息生产者
 */
@EnableBinding(Source.class)
@Slf4j
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;

    public String send() {
        String uuid = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload( uuid).build() );
        log.info( "IMessageProviderSend======UUID====={}", uuid );
        return null;
    }
    public String SendMsg(String msg) {
        return null;
    }
}