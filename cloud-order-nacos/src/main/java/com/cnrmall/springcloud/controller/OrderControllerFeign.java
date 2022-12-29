package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.service.PaymentServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author David
 * @date 2022/12/28 19:53
 */
@RestController
@Slf4j
public class OrderControllerFeign {

    @Resource
    private PaymentServiceFeign serviceFeign;

    @GetMapping("/pay/port")
    public String getPaymentPort(){
        return serviceFeign.getPaymentPort() ;
    }
}
