package com.xunmall.example.java.guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;

/**
 * @author Gimgoog
 * @date 2018/1/30
 */
public class MultiMapDemo {
    public static void main(String[] args) {
        // Multimap是把键映射到任意多个值的一般方式
        Multimap<String, Integer> map = HashMultimap.create();
        // key相同时不会覆盖原value
        map.put("a", 1);
        map.put("a", 2);
        map.put("a", 3);
        System.out.println(map);
        // 返回的是集合
        System.out.println(map.get("a"));
        // 返回所有”键-单个值映射”的个数,而非不同键的个数
        System.out.println(map.size());
        // 返回不同key的个数
        System.out.println(map.keySet().size());
        Map<String, Collection<Integer>> mapView = map.asMap();
        mapView.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value.size());
        });
    }
}
