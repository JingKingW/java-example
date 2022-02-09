package com.xunmall.example.java.stream;

/**
 * Created by Gimgoog on 2017/12/14.
 */
public class RandomDemo {

    public static void main(String[] args){
        int count = (int) Math.floor(Long.parseLong("200000")/2000 );
        System.out.println(count);

        String mobile = "15109852344";
        String hid = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        System.out.println(hid);
    }
}
