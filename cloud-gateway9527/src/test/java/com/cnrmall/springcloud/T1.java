package com.cnrmall.springcloud;

import java.time.ZonedDateTime;

/**
 * @author David
 * @date 2022/12/20 21:43
 */
public class T1 {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);

        //2022-12-20T21:44:34.610+08:00[Asia/Shanghai]
    }
}
