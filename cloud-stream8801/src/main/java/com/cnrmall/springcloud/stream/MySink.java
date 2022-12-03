package com.cnrmall.springcloud.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 *  自定义  INPUT
 */
public interface MySink {

    String INPUT1 = "input1" ;

    @Input(MySink.INPUT1)
    SubscribableChannel inut1();

    String INPUT2 = "input2" ;

    @Input(MySink.INPUT2)
    SubscribableChannel inut2();

}
