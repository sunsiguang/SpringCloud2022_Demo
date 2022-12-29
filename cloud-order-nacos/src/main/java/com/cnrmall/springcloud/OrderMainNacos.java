package com.cnrmall.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author David
 * @date ${DATE} ${TIME}
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //启动OpenFeign
public class OrderMainNacos {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainNacos.class,args) ;
    }
}