package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.entities.CommonResult;
import com.cnrmall.springcloud.entities.Payment;
import com.cnrmall.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/query/{id}")
    public CommonResult queryPayment(@PathVariable int id){
        CommonResult cr = new CommonResult();
        Payment payment = paymentService.selectById(id);
        cr.setData(payment);
        if (payment!=null){
            cr = new CommonResult(200,"查询成功",payment);
        }else{
            cr = new CommonResult(401,"查询失败",payment);
        }
        return cr;
    }


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

}