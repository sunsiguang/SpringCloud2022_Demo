package com.cnrmall.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cnrmall.springcloud.customer.handler.FeignBlockHandler;
import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import com.cnrmall.springcloud.service.PaymentServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author David
 * @date 2023/1/11 17:04
 */
@RestController
@Slf4j
public class OrderControllerFeignFallback {

    @Resource
    private PaymentServiceFeign serviceFeign;

    /**
     * Feign 调用 ，如果被调用端异常，服务降级
     *
     * @FeignClient(value = "${service-url.payment-service}" , fallback = PaymentServiceFeignFallback.class )
     * Fallback
     * @param id
     * @return
     */
    @GetMapping("/pay/fallback/feign/{id}")
//    @SentinelResource(value = "fallback_feign",   blockHandlerClass = FeignBlockHandler.class , blockHandler = "handlerException")   不好使
    public CommonResult<Payment> getPaymentSQLFeign(@PathVariable("id") Long id ){
        CommonResult<Payment> result = serviceFeign.getPaymentSQL(id);
        return result ;
    };

}
