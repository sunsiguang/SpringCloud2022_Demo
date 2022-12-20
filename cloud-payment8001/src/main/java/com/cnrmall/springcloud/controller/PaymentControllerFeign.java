package com.cnrmall.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author David
 * @date 2022/12/19 17:16
 */
@RestController
@Slf4j
public class PaymentControllerFeign {

    @Value("${server.port}")
    private String serverPort ;

    @GetMapping("/pay/timeout")
    public String getPaymentFeignTimeout(){

        try {
            TimeUnit.SECONDS.sleep( 5 );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return serverPort;
    }
}
