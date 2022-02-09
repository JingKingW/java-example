package com.xunmall.example.java.math;

import java.math.BigInteger;

/**
 * 取模工具
 * Created by owen5 on 2017/4/11.
 */
public class ModulaUtils {
    public static String ModulaHash(long id,int modulaNumber){
        checkNull(id, modulaNumber);
        BigInteger divid= BigInteger.valueOf(id);
        BigInteger modula= BigInteger.valueOf(modulaNumber);
        String result=String.valueOf(divid.remainder(modula));
        return result;
    }

    private static void checkNull(long id, int modulaNumber)
            throws IllegalAccessError {
        if(id<0L){
            throw new IllegalAccessError("input divisor less than zero!");
        }
        if(modulaNumber<=0){
            throw new IllegalAccessError("input modulaNumber less than zero!");
        }
    }

    public static void main(String[] agrs){
        String result = ModulaUtils.ModulaHash(72157623227191517L, 5);
        System.out.println(result);
    }
}
