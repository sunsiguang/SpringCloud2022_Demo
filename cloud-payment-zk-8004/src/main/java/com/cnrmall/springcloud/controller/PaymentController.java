package com.cnrmall.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/zk")
    public  String getZKService(){
       String str = "SpringCloud Zookeeper:"+serverPort+"\t"+ UUID.randomUUID().toString();
       log.info(str);
       return  str;
    }

    @GetMapping(value = "/consul")
    public  String getConsulService(){
        String str = "SpringCloud Consul:"+serverPort+"\t"+ UUID.randomUUID().toString();
        log.info(str);
        return  str;
    }
}