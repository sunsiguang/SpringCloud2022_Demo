package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entites.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 2022/12/28 19:54
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort ;


    @GetMapping("/port")
    public String queryPaymentPort(){
        return serverPort ;
    }

}
