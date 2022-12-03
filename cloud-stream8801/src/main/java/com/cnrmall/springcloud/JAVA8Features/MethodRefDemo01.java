package com.cnrmall.springcloud.JAVA8Features;

import java.util.function.Function;

public class MethodRefDemo01 {

    public static void main(String[] args) {

        FunctionGeneric<String> strName = s -> System.out.println(s);
        strName.fun("Lambda表达式没有使用方法引用");
        //方法引用
        FunctionGeneric<String> strName2 = System.out::println;
        strName2.fun("使用方法引用");

        //构造器引用
        Function<String, Integer> fun1 = (num) -> new Integer(num);
        Function<String, Integer> fun2 = Integer::new;

        //数组引用
        Function<Integer,Integer[]> fun3 = (num) ->new Integer[num];
        Function<Integer,Integer[]> fun4 = Integer[]::new;
    }
}