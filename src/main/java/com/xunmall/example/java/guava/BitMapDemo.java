package com.xunmall.example.java.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class BitMapDemo {
    public static void main(String[] args) {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("sina", "sina.com");
        biMap.put("qq", "qq.com");
        // 会覆盖原来的value
        biMap.put("sina", "sina.cn");
       /*
         在BiMap中,如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常
         如果对特定值,你想要强制替换它的键，请使用 BiMap.forcePut(key, value)
        */
        //biMap.put("tecent","qq.com"); //抛出异常
        // 强制替换key
        biMap.forcePut("tecent", "qq.com");
        System.out.println(biMap);
        // 通过value找key
        System.out.println(biMap.inverse().get("sina.com"));
        // true
        System.out.println(biMap.inverse().inverse() == biMap);

    }
}
