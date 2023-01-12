package com.cnrmall.springcloud.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.IllegalFormatCodePointException;

/**
 * @author David
 * @date 2023/1/10 20:03
 */

@RestController
@Slf4j
public class OrderControllerSentinelFallback {

    @Value("${service-url.payment-url}")
    private String PAYMENT_URL ;

    @Resource
    private RestTemplate restTemplate ;

    /**
     * restTemplate &  Sentinel  降级&异常处理 测试
     * fallback  只负责业务异常
     * blockHandler 只负责 Sentinel  配置异常
     * 测试地址： http://localhost:83/pay/fallback/{id}
     * @param id
     * @return
     */
    @GetMapping("/pay/fallback/{id}")
    @SentinelResource(value = "fallback",  fallback = "handlerFallback" , blockHandler =  "blockHandler" ,
                                       exceptionsToIgnore = {IllegalFormatCodePointException.class}  )   //配置忽略异常，没有降级效果, 异常直接抛出
    public CommonResult<Payment> getPaymentSQLFallback(@PathVariable("id") Long id ){
        CommonResult commonResult = restTemplate.getForObject(PAYMENT_URL + "/pay/sql/" + id, CommonResult.class);

        //异常处理补充
        if (id == 4 ){
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数异常......") ;
        }else if (id == 5 ) {
            //配置忽略异常，假如报该异常，不会有fallback方法兜底，没有降级效果, 异常直接抛出
            throw new IllegalFormatCodePointException(1) ;
        }else if (commonResult==null){
            throw new NullPointerException("NullPointerException, 空指针异常.....");
        }
        return commonResult ;
    };


    /**
     * BlockException 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.，管理配置违规异常，不管运行时异常
     * blockHandler 处理方法
     * 需要在 sentinel 控制台配置，限流异常才会生效。
     * @param id
     * @param blockException
     * @return
     */
    public CommonResult<Payment> blockHandler(Long id , BlockException blockException ){
        return new CommonResult<>(408, "blockHandler，sentinel 限流异常 , 异常内容: " + ExceptionUtil.getMessage(blockException), new Payment(id, null, null));
    }


    /**
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.,管理运行时异常，程序运行内部异常
     * Fallback 无需在sentinel 控制台配置，就可以生效
     * @param id
     * @param e
     * @return
     */
    public CommonResult<Payment> handlerFallback(Long id , Throwable  e ){

        return new CommonResult<>(409, "兜底异常，handlerFallback,异常内容: " + e.getMessage(), new Payment(id, null, null));
    }

}
