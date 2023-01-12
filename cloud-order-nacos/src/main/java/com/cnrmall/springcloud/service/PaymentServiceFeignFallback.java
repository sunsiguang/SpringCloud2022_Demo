package com.cnrmall.springcloud.service;

import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entites.Payment;
import org.springframework.stereotype.Component;

/**
 * @author David
 * @date 2023/1/11 16:31
 */
@Component
public class PaymentServiceFeignFallback implements  PaymentServiceFeign {

    @Override
    public String getPaymentPort() {
        return "getPaymentPort服务降级返回，PaymentServiceFeignFallback";
    }

    /**
     * Feign 调用 降级处理方法
     * @param id
     * @return
     */
    @Override
    public CommonResult<Payment> getPaymentSQL(Long id) {
        CommonResult<Payment> commonResult = new CommonResult<>(406, "服务降级返回，PaymentServiceFeignFallback ", new Payment(id,null,null));
        return commonResult;
    }
}
