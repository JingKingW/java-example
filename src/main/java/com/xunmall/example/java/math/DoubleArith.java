package com.xunmall.example.java.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * double 精度工具类
 */
public class DoubleArith {
    private static final Logger logger = LoggerFactory.getLogger(DoubleArith.class);

    private DoubleArith() {
    }

    //默认加减乘除运算精度
    private static final int DEF_DIV_SCALE = 4;

    /**
     * 为了解决double 精度丢失问题这里增加 对double类型 加减乘除 方法
     */

    //精确的加法运算
    public static double add(double d1, double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.add(b2).doubleValue();
    }

    //精确的减法运算
    public static double sub(double d1, double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.subtract(b2).doubleValue();
    }

    //提供精确的乘法运算
    public static double mul(double d1, double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.multiply(b2).doubleValue();
    }

    //提供（相对）精确的除发运算 当除不尽的情况时 精确到小数点的 DEF_DIV_SCALE 的位数字四舍五入
    public static double div(double d1, double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args){
        double result = div(2.12313,3.12312);
        System.out.println(result);

        BigDecimal b1 = BigDecimal.valueOf(12.1211);
        BigDecimal b2 = BigDecimal.valueOf(100.1121);
        double doubleValue = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(doubleValue);
        System.out.println(doubleValue * 100 > 12);
    }
}
