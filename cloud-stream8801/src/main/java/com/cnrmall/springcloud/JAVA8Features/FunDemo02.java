package com.cnrmall.springcloud.JAVA8Features;

import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
public class FunDemo02 {

    public static void main(String[] args) {
        FunDemo02 demo01 = new FunDemo02();
        demo01.functionTest();
        demo01.consumerTest();
        demo01.supplierTest();
        demo01.predicateTest();

    }
    public void functionTest(){
//        非Lambda表达式
//        Function function = new Function<String,String>(){
//            @Override
//            public String apply(String s) {
//                return s;
//            }
//        };
//        log.info("函数型接口 :{}",function.apply("没有使用Lambda表达式"));

        Function function = s -> s;
        log.info("函数型接口：{}",function.apply("Function Demo"));
    }

    public void consumerTest(){
//        非Lambda表达式
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                log.info(s);
//            }
//        };
//        consumer.accept("消费型函数：没有使用Lambda表达式");
//        使用Lambda表达式
        Consumer<String> consumer = s -> log.info(s);
        consumer.accept("消费型函数：Consumer Demo");
    }
    public void supplierTest(){
//        非Lambda表达式
//        Supplier supplier = new Supplier<String>(){
//            @Override
//            public String get() {
//                return "供给型接口：没有使用Lambda表达式";
//            }
//        };
//        log.info(String.valueOf(supplier.get()));

        Supplier supplier =  () -> "供给型接口：Supplier Demo";
        log.info(String.valueOf(supplier.get()));
    }
    public void predicateTest() {
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.equals("Predicate Demo");
//            }
//        };
//        log.info("断言型接口：{}",predicate.test("没有使用Lambda表达式"));
        Predicate<String> predicate = s -> s.equals("Predicate Demo");
        log.info("断言型接口：{}",predicate.test("Predicate Demo"));
    }
}