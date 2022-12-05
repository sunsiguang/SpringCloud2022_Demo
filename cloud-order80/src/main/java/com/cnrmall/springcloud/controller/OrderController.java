package com.cnrmall.springcloud.controller;


import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

//    public static final String PAYMENT_URL ="http://localhost:8001" ;  //单机调用
    public static final String PAYMENT_URL ="http://CLOUD-PAYMENT-SERVICE" ;  // 通过服务调用，ribbon

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/order/create")
    public CommonResult<Payment> create(Payment payment){

       return restTemplate.postForObject(PAYMENT_URL + "/pay/create",payment,CommonResult.class);
    }
}