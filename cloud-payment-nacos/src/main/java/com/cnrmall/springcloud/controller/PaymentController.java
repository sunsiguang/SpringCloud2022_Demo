package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.PaymentMain9001;
import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

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

    /**
     * Sentinel 测试
     * 模拟正常查询的测试
     * @param id
     * @return
     */
    @GetMapping("/sql/{id}")
    public CommonResult<Payment> getPaymentSQL(@PathVariable("id") Long id ){
        if (id < 6) {
            Random random = new Random();
            Payment payment = new Payment(id, "Sentinel 测试,ID:" + id, random.nextDouble());
            CommonResult<Payment> commonResult = new CommonResult<Payment>(200, "query SQL Test,ServerPort:" + serverPort, payment);
            return  commonResult ;
        }else {
            return  null;
        }
    };

}
