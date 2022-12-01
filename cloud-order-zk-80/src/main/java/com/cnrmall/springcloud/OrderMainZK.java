package com.cnrmall.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient      //客户发现注册服务zookeeper和consul 使用
public class OrderMainZK {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainZK.class,args);
    }
}