package com.cnrmall.springcloud.customer.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cnrmall.springcloud.entites.CommonResult;

/**
 * @author David
 * @date 2023/1/9 17:07
 */
public class CustomerBlockHandler {

    /**
     * 管理配置违规异常，不管运行时异常
     * @param be
     * @return
     */
    public static CommonResult handlerException(BlockException be){
           return  new CommonResult(401, be.getClass().getCanonicalName(), "用户自定义异常 ，global HandlerException 1 ");
    }

    public static CommonResult handlerException2(BlockException be){
        return  new CommonResult(402, be.getClass().getCanonicalName(), "用户自定义异常 ，global HandlerException 2 ");
    }
}
