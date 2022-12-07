package com.cnrmall.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderMainZK {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainZK.class,args);

    }
}