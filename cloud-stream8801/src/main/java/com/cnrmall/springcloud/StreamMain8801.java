package com.cnrmall.springcloud;


import com.cnrmall.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

import javax.xml.transform.Source;

//@EnableBinding({Source.class, Sink.class})
@SpringBootApplication
public class StreamMain8801 implements CommandLineRunner {

    @Autowired
    private IMessageProvider messageProviderImpl;

    public static void main(String[] args) {
        SpringApplication.run(StreamMain8801.class);
    }

    public void run(String... args) throws Exception {
//        messageProviderImpl.send();
    }
}