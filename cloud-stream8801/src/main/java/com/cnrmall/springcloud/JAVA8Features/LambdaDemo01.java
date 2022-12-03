package com.cnrmall.springcloud.JAVA8Features;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LambdaDemo01 {

    public static void main(String[] args) {
        Runnable t1 =new Runnable(){
            @Override
            public void run(){
                log.info("我是没有使用Lambda表达式：不简洁");
            }
        };

        Runnable t2 = () -> log.info("我是使用Lambda表达式：简洁、灵活");
        t1.run();
        t2.run();

    }
}