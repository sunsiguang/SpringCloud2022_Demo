package com.cnrmall.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 2023/1/12 13:28
 * 热点参数 Demo
 *
 */
@RestController
@Slf4j
public class ParamFlowController {

    /**
     * http://localhost:8401/testHotKey?p1=aaa&p2=bbb
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
//    @SentinelResource(value = "hotKey")  // 如果没加兜底方法，会抛异常 java.lang.reflect.UndeclaredThrowableExceptionjava.lang.reflect.UndeclaredThrowableException
   @SentinelResource(value = "hotKey", blockHandler = "paramBlockHandler" )  // 注意这里需要使用注解标识，否则不生效。
    public String testHotKey(@RequestParam(value = "p1", required = false )  int p1 ,       //如果配置参数例外项，此参数类型必须和Sentinel 配置的参数类型一致，才生效，否则不生效。
                                                 @RequestParam(value =  "p2", required = false)  String p2 ){

        return  "-----热点参数限流策略 Demo -----p1:"+p1+", p2:"+p2;
    }

    /**
     * 热点参数 限流后的兜底方法
     * @return
     */
    public String paramBlockHandler(int p1, String p2 , BlockException blockException){
            return  "-----热点参数 限流后的兜底方法-----"+p1+","+p2 ;
    }

}
