package com.cnrmall.springcloud.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 自定义 OUTPUT
 */
public interface MySource {

    String  OUTPUT1 = "output1" ;

    @Output(MySource.OUTPUT1)
    MessageChannel output1();

    String  OUTPUT2 = "output2" ;

    @Output(MySource.OUTPUT2)
    MessageChannel output2();

}
