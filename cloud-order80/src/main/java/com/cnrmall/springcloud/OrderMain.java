package com.cnrmall.springcloud;

import com.cnrmall.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)  //自定义IRule 规则
public class OrderMain {

    public static void main(String[] args) {

        SpringApplication.run(OrderMain.class,args);
    }
}