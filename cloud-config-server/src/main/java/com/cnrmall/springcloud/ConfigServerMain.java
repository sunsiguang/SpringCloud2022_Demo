package com.cnrmall.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author David
 * @date ${DATE} ${TIME}
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer  // Config 配置类
public class ConfigServerMain {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerMain.class,args);
    }
}

//测试地址
//http://config8888.com:8888/config-dev.yml