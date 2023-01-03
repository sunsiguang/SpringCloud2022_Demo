package com.cnrmall.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 2023/1/3 14:41
 */
@RestController
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        return  "testA" ;
    }

    @GetMapping("/testB")
    public String testB(){
        return  "testB" ;
    }

}
