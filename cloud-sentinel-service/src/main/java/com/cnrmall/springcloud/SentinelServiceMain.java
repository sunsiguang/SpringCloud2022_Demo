package com.cnrmall.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author David
 * @date 2022/1/3
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(SentinelServiceMain.class,args);
    }
}