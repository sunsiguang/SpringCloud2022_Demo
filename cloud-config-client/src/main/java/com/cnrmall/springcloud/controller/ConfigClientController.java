package com.cnrmall.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 2022/12/22 14:38
 */
@RestController
@Slf4j
@RefreshScope  //刷新配置文件  通过post请求，手动刷新：curl -X POST "http://localhost:3355/actuator/refresh"
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configinfo")
    public  String getConfigInfo(){
        return configInfo;
    }

}
