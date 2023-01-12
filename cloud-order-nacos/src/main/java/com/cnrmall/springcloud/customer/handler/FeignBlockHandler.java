package com.cnrmall.springcloud.customer.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cnrmall.springcloud.entites.CommonResult;

/**
 * @author David
 * @date 2023/1/11 17:55
 */


public class FeignBlockHandler {

    /**
     * Sentinel  降级处理方法，自定义异常处理
     * @return
     */
    public static CommonResult handlerException(BlockException be){
        return  new CommonResult(401, be.getClass().getCanonicalName(), "用户自定义异常 ，global HandlerException ");
    }

}
