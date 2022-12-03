package com.cnrmall.springcloud.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cnrmall.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentDao extends BaseMapper<Payment>{

}