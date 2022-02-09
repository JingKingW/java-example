package com.xunmall.example.java.math;

import java.text.NumberFormat;

/**
 * @Author: WangYanjing
 * @Date: 2019/6/5 10:25
 * @Description:
 */
public class BigDecimalDemo {

    public static void main(String[] args) {
        double x = 0.21111111111111111;
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(3);
        String s = ddf1.format(x);
        System.out.print(s);
    }
}
