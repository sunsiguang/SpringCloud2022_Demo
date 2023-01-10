package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entites.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author David
 * @date 2023/1/3 14:41
 */
@RestController
public class FlowLimitController {

    /**
     * 模拟 订单服务
     * @return
     */
    @GetMapping("/order")
    public CommonResult order(){
        CommonResult result = new CommonResult();
        result.setData("cloud-order-service is UP" );
        return  result;
    }

    /**
     * 模拟 支付服务
     * @return
     */
    @GetMapping("/payment")
    public CommonResult payment(){
        CommonResult result = new CommonResult();
        result.setData("cloud-payment-service is UP" );
        return  result;
    }

    /**
     * 模拟 线程数限流
     * @return
     */
    @GetMapping("/flowThread")
    public String flowThread(){
        try {
            //当前线程，处理需要800毫秒
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return  "flowThread" ;
    }

    

}
