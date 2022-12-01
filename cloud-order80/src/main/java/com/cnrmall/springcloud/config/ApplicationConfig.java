package com.cnrmall.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    /**
     * RestTemplate提供了多种便捷访问远程Http服务的方法是一种简单便捷的访问restful服务模板类，
     * 是Spring提供的用于访问Rest服务的客户端模板工具集
     *
     * 使用restTemplate访问restful接口非常的简单粗暴无脑
     * (url,requestMap, ResponseBean.class)
     * 这三个参数分别代表REST请求地址、请求参数、HTTP响应转换被转换成的对象类型
     * API:
     * https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html
     * @return
     */
    @Bean
    @LoadBalanced   //注意: 负载均衡 微服务名替换为具体的ip:port
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    public IRule myRule(){
//        return  new RandomRule(); //定义随机Rule
//    }

}