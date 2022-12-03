package com.cnrmall.springcloud.JAVA8Features;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class LambdaDemo04 {
    public static void main(String[] args) {
        dateType();
    }
    public static void dateType(){
        Consumer<String> consumer = (String s) -> log.info(s);
        consumer.accept("Hello World !");

        Consumer<String> consumer1 = (s) -> log.info(s);
        consumer1.accept("Hello don't date type !");
    }
}