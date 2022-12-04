package com.cnrmall.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    /**
     * RestTemplate提供了多种便捷访问远程Http服务的方法是一种简单便捷的访问restful服务模板类，
     * 是Spring提供的用于访问Rest服务的客户端模板工具集
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}