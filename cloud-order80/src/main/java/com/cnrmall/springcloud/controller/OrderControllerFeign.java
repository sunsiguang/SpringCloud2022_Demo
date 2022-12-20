package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import com.cnrmall.springcloud.service.PaymentServiceFeign;
import feign.Feign;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.concurrent.TimeUnit;

/**
 * @author David
 * @date 2022/12/19 16:57
 */
@RestController
@Slf4j
public class OrderControllerFeign {

    @Resource
    private PaymentServiceFeign paymentServiceFeign ;


    @GetMapping("/feign/pay/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")  long id ){
        return paymentServiceFeign.getPayment(id) ;
    }

    @GetMapping("/feign/pay/timeout")
    public String getPaymentFeignTimeout(){
        return paymentServiceFeign.getPaymentFeignTimeout();
//        PaymentServiceFeign target = Feign.builder()
//                .options(new Request.Options(2, TimeUnit.SECONDS, 3, TimeUnit.SECONDS, true))
//                .target(PaymentServiceFeign.class,"CLOUD-PAYMENT-SERVICE");
//        return target.getPaymentFeignTimeout();
    }

}
