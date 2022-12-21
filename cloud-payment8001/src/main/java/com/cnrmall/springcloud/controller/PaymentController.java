package com.cnrmall.springcloud.controller;


import cn.hutool.core.util.StrUtil;
import com.cnrmall.springcloud.entites.CommonResult;
import com.cnrmall.springcloud.entities.Payment;
import com.cnrmall.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort ;
    @Autowired
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient ;  //客户端发现

    @Value("${spring.application.name}")
    private String applicationName;



    @GetMapping("/lb")
    public String queryPaymentLB(){
        return serverPort;
    }

    @GetMapping("/query/{id}")
    public CommonResult queryPayment(@PathVariable int id){
        CommonResult cr = new CommonResult();
        Payment payment = paymentService.selectById(id);
        cr.setData(payment);
        if (payment!=null){
            cr = new CommonResult(200,"查询成功,serverPort:"+serverPort ,payment);
        }else{
            cr = new CommonResult(401,"查询失败,serverPort:"+serverPort ,payment);
        }
        return cr;
    }

    /**
     * 坑2
     * @RequestBody
     * 主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；而最常用的使用请求体传参的无疑是POST请求了，
     * 所以使用@RequestBody接收数据时，一般都用POST方式进行提交。
     *
     * @param payment
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        CommonResult cr = null;
        boolean flag = paymentService.insert(payment);
//        int i = 10/0;
        if (flag){
            cr = new CommonResult(200,"插入数据库成功");
        }else{
            cr = new CommonResult(401,"插入数据库失败");
        }
        return cr;
    }


    /**
     *  注册的服务
     *  注册是实例
     * @return
     */
    @GetMapping("/discovery")
    public Map discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("====service====,{}",service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances(applicationName);
        for (ServiceInstance instance : instances) {
            instance.getHost();
            instance.getInstanceId();
            instance.getPort();

            System.out.println(StrUtil.toString(instance));
            log.info("======instance=====,{}", instance.getUri());
        }
//        ModelAndView modelAndView = new ModelAndView();
        Map map = new HashMap<String,Object>();
        map.put("service",services);
        map.put("instances", instances );
        return  map;
    }

}