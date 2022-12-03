package com.cnrmall.springcloud.JAVA8Features;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class LambdaDemo02 {

    public static void main(String[] args) {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                log.info(s);
            }
        };
        consumer.accept("爱与被爱的区别");

        Consumer<String> consumer1 = (s) -> log.info(s);
        consumer1.accept("接受爱不一定爱对方，爱一定付出真心爱");

        Consumer<String> consumer2 = s -> log.info(s);
        consumer2.accept("Lambda只有一个参数时，可以省略（）");
    }
}