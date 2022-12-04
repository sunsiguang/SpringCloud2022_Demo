package com.cnrmall.springcloud.entities;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("payment")
public class Payment implements Serializable{

//    @TableId(type = IdType.AUTO,value = "ID")
    @TableId(type = IdType.AUTO)  //主键自增
    private Long id;
    private String name ;
    private Double amount;

}