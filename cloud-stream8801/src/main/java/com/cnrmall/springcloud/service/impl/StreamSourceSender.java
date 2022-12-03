package com.cnrmall.springcloud.service.impl;

import com.cnrmall.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StreamSourceSender implements IMessageProvider {

    @Autowired
    private Source source ;

    public String send() {
        return null;
    }

    public String SendMsg(String msg) {
        source.output().send( MessageBuilder.withPayload( msg ).build()  ) ;
        log.info("StreamSourceSender====>>>>{}" ,  msg );
        return null;
    }
}