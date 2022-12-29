package com.cnrmall.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author David
 * @date 2022/12/29 10:40
 */
//@FeignClient(value = "cloud-payment-service")  //小写，和Nacos上一致， IDEA中字母大小写切换快捷键: ctr + shift + u转换大小写
@FeignClient(value = "${service-url.payment-service}")  //读取配置文件
//feign.FeignException$ServiceUnavailable: [503] during [GET] to [http://CLOUD-PAYMENT-SERVICE/pay/port] [PaymentServiceFeign#getPaymentPort()]: [Load balancer does not contain an instance for the service CLOUD-PAYMENT-SERVICE]
public interface PaymentServiceFeign {

    @GetMapping("/pay/port")
    public String getPaymentPort();
}
