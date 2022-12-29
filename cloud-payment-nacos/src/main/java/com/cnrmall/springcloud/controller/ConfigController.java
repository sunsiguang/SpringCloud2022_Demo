package com.cnrmall.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 2022/12/29 14:06
 *
 *  http://nacos.io/zh-cn/docs/quick-start-spring-cloud.html
 * ${prefix}-${spring.profiles.active}.${file-extension}
 * cloud-payment-service-dev.yml
 * prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
 *
 * spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。
 * 注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}
 *
 * file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。
 * 目前只支持 properties 和 yaml 类型。
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${config.info}")
    private String configInfo;

    @Value("${server.port}")
    private String serverPort ;

    @GetMapping("/info")
    public String getConfiginfo(){
      return  configInfo + ", serverPort : " +serverPort ;

    }


}
