package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author David
 * @date 2022/12/29 15:14
 */
@RestController
@RequestMapping("/config")
@Slf4j
public class ConfigController {

    @Value("${service-url.payment-url}")
    private String PAYMENT_URL ;     //= "http://cloud-payment-service"  ;  // 服务名，和nacos一致, 不要加static
    @Resource
    private RestTemplate restTemplate ;

    @GetMapping("/info")
    public String getConfigInfo(){
        return restTemplate.getForObject(PAYMENT_URL+"/config/info", String.class ) ;
    }

}
