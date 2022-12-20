package com.cnrmall.springcloud.service;

import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author David
 * @date 2022/12/19 16:54
 */
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentServiceFeign {


    @GetMapping(value = "/pay/query/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")  long id );

    @GetMapping("/pay/timeout")
    public String getPaymentFeignTimeout();

//    @RequestLine("POST /account/{id}")
//    Account getAccountInfo(@Param("id") String id);
}
