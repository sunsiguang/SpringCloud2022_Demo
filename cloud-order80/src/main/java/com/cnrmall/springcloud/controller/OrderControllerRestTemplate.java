package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderControllerRestTemplate {

    public static final String PAYMENT_URL ="http://CLOUD-PAYMENT-SERVICE" ;  // 通过服务调用，ribbon
    @Resource
    private RestTemplate restTemplate ;

    //RestTemplate . getForObject 返回对象为响应体中数据转化成的对象，可以理解为Json
    @GetMapping("/order/payment/getForObject/{id}")
     public CommonResult getPayment(@PathVariable("id")  long id ){

        CommonResult result = restTemplate.getForObject(PAYMENT_URL + "/pay/query/" + id, CommonResult.class);
        return  result ;
    }

    //返回对象为ResponseEntity , 包含了响应中的一些重要信息，比如响应头，响应状态码，响应体
    @GetMapping("/order/payment/getForEntity/{id}")
    public CommonResult<Payment> getPaymentForEntity(@PathVariable("id") long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/pay/query/" + id, CommonResult.class);
        boolean xxSuccessful = entity.getStatusCode().is2xxSuccessful();
        if (xxSuccessful){
            HttpHeaders headers = entity.getHeaders();
            log.info( "headers:" + headers);
            return entity.getBody();
        }else{
            return new CommonResult<Payment>(444, "操作失败") ;
        }
    }

}