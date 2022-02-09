package com.xunmall.example.java.util;

/**
 * Created by SHCL on 2018/8/2.
 */
public class OperatorDemo {
    public static void main(String[] args) {
        int value = 20;
        System.out.println(value >> 1);
        System.out.println(value << 1);
        System.out.println(value >> 2);
        System.out.println(value << 2);
        System.out.println(value >> 3);
        System.out.println(value << 3);

        String publishTime = "2014-01-07大陆上映";
        String string = publishTime.length() > 10? publishTime.substring(0,10):"";
        System.out.println(string);

    }
}
