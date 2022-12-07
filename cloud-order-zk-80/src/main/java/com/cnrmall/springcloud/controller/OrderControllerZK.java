package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entites.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderControllerZK {

        //payment 服务的注册名
        public static final String  PAYMENT_URL ="http://cloud-payment-service";

        @Resource
        private RestTemplate restTemplate ;

        @GetMapping("/payment/zk")
        public String getPaymentInfo(){
                 //调用payment Service 服务
                String result = restTemplate.getForObject( PAYMENT_URL + "/pay/zk",String.class);
                log.info(result);
                return result ;
        }

}