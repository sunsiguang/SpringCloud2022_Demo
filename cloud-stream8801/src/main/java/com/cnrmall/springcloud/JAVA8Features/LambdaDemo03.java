package com.cnrmall.springcloud.JAVA8Features;


import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@Slf4j
public class LambdaDemo03 {
        public static void main(String[] args) {
        CompareOldMethod(12,10);
        findMaxValue(12,10);
        findMinValue(12,10);
        findMinValue2(15,13);
    }
    //    没有使用Lambda表达式比较大小
    public static void CompareOldMethod(int num1,int num2){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                log.info("o1:{}",o1);
                log.info("o2:{}",o2);
                return o1 < o2 ? o2 : o1;
            }
        };
        log.info("OldFindMaxValue:{}",comparator.compare(num1,num2));
    }

//    使用lambda表达式 Lambda有两个参数时，并且有返回值ublic
public static void findMaxValue(int num1,int num2){
        Comparator<Integer> comparatorMax = (o1, o2) ->{

            log.info("o1:{}",o1);
            log.info("o2:{}",o2);
            return (o1<o2)? o2 :(o1);
        };

        log.info("findMaxValue:{}",(comparatorMax.compare(num1,num2)));

    }
    public static void findMinValue(int num1,int num2){
        Comparator<Integer> comparatorMin =  (o1, o2) -> {
            log.info("o1:{}",o1);
            log.info("o2:{}",o2);
            return (o1 < o2) ? o1 : o2;
        };
        log.info("FindMinValue:{}",comparatorMin.compare(num1,num2));
    }

    //当Lambda体只有一条语句的时候，return和{}可以省略掉
    public static void findMinValue2(int num1,int num2){
        Comparator<Integer> comparatorMin =  (o1, o2) -> (o1 < o2) ? o1 : o2;
        log.info("FindMinValue:{}",comparatorMin.compare(num1,num2));
    }


}