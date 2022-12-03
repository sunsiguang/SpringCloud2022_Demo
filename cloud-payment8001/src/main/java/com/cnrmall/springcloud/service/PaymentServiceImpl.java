package com.cnrmall.springcloud.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cnrmall.springcloud.dao.PaymentDao;
import com.cnrmall.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentDao,Payment> implements PaymentService{
}