package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entites.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author David
 * @date 2023/1/4 15:23
 * 熔断降级 Demo
 */
@RestController
@Slf4j
public class DegradeController {

    /**
     * 慢调用比例 (SLOW_REQUEST_RATIO)：选择以慢调用比例作为阈值，需要设置允许的慢调用 RT（即最大的响应时间），请求的响应时间大于该值则统计为慢调用。
     * 当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，并且慢调用的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。
     * 经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求响应时间小于设置的慢调用 RT 则结束熔断，若大于设置的慢调用 RT 则会再次被熔断。
     * @return
     */
    @GetMapping("/slow_request_ratio")
    public CommonResult slow_request_ratio() {
        try {
            TimeUnit.MILLISECONDS.sleep(800);
            CommonResult result = new CommonResult();
            result.setData("breakerDemo");
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * 异常比例 (ERROR_RATIO)：当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，并且异常的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。
     * 经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。
     * 异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%。
     * @return
     */
    @GetMapping("/error_ratio")
    public CommonResult error_ratio(){
            try {
                    //TODO
            }catch (Exception e ){
                    e.printStackTrace();;
            }
            return  null ;
    }


    /**
     * 异常数 (ERROR_COUNT)：当单位统计时长内的异常数目超过阈值之后会自动进行熔断。
     * 经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），
     * 若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。
     * @return
     */
    @GetMapping("/error_count")
    public CommonResult error_count(){
        try {
            int i = 10/0 ;
        }catch (Exception e ){
            e.printStackTrace();;
        }
        return  null ;
    }


}
