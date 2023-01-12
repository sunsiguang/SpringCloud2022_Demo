package com.cnrmall.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cnrmall.springcloud.customer.handler.CustomerBlockHandler;
import com.cnrmall.springcloud.entites.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 2023/1/9 16:23
 *  自定义 限流规则
 *  注解限流规则
 */
@RestController
public class CustomerLimitController {

    /**
     * 按资源名限流，使用自定义 handleException ，异常处理规则
     * @return
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource(){
            return  new CommonResult(200, "按资源名限流",  null );
    }
    public CommonResult handleException(BlockException blockException){
        //异常兜底处理方法
        return  new CommonResult(456, blockException.getClass().getCanonicalName(), "\t 服务异常，不可用" );
    }

    /**
     * 按byUrl ，使用 sentinel 默认的异常处理规则
     * 如果没有使用兜底异常处理，使用 sentinel 默认规则
     * @return
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl" )
    public CommonResult byUrl(){
        return  new CommonResult(200, "按URL限流",  null );
    }

    /**
     * value：资源名称，必需项（不能为空）
     * entryType：entry 类型，可选项（默认为 EntryType.OUT）
     * blockHandler / blockHandlerClass: blockHandler 对应处理 BlockException 的函数名称，可选项。blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * fallback：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。fallback 函数签名和位置要求：
     *          返回值类型必须与原函数返回值类型一致；
     *          方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     *          fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * defaultFallback（since 1.6.0）：默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）。默认 fallback 函数可以针对所以类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。若同时配置了 fallback 和 defaultFallback，则只有 fallback 会生效。defaultFallback 函数签名要求：
     *          返回值类型必须与原函数返回值类型一致；
     *          方法参数列表需要为空，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     *          defaultFallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * exceptionsToIgnore（since 1.6.0）：用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出。
     *
     * 业务和异常处理类，解耦处理
     * 注意：注解方式埋点不支持 private 方法。
     * 注意：1.6.0 之前的版本 fallback 函数只针对降级异常（DegradeException）进行处理，不能针对业务异常进行处理。
     *  https://sentinelguard.io/zh-cn/docs/annotation-support.html
     */
    @GetMapping("/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass =  CustomerBlockHandler.class , blockHandler = "handlerException2")    // 对应的 `handleException` 函数需要位于 `CustomerBlockHandler` 类中，并且必须为 static 函数.
    public CommonResult customerBlockHandler(){
        return  new CommonResult(200, "用户自定义处理类，返回成功",  null );
    }
}
