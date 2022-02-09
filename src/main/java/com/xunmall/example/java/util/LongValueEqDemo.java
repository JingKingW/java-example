package com.xunmall.example.java.util;

import java.util.Objects;

/**
 * @Author: WangYanjing
 * @Date: 2019/10/16 9:53
 * @Description:
 */
public class LongValueEqDemo {

    public static void main(String[] args) {

        Long abc = 1111L;
        Long xyz = 1111L;

        if (Objects.equals(abc, xyz)) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }

        if (abc.longValue() == xyz.longValue()) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }

        if (abc.equals(xyz)) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }

        long abc1 = 111L;
        long xyz1 = 111L;

        if (abc1 == xyz1) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }
    }


}
