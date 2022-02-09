package com.xunmall.example.java.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gimgoog on 2017/11/23.
 */
public class CollectionAddDemo {

    public static void main(String[] args) {
        /*
           List相关
           forEach , removeIf ,replaceAll , sort , spliterator , stream , parallelStream
         */
        List<String> list = new ArrayList<>(Arrays.asList("i", "love", "shanghai", "and", "huaian"));
        list.forEach(str -> {
            if (str.length() > 3) {
                System.out.println(str);
            }
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<String> list1 = new ArrayList<>(Arrays.asList("i", "love", "shanghai", "and", "huaian"));
        list1.removeIf(str -> str.length() > 3);
        list1.forEach(str -> {
            System.out.println(str);
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<String> list2 = new ArrayList<>(Arrays.asList("i", "love", "shanghai", "and", "huaian"));
        list2.replaceAll(str -> {
            if (str.length() > 3) {
                return str.toUpperCase();
            }
            return str;
        });
        list2.forEach(str -> {
            System.out.println(str);
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<String> list3 = new ArrayList<>(Arrays.asList("i", "love", "shanghai", "and", "huaian"));
        list3.sort((str1, str2) -> str1.length() - str2.length());
        list3.forEach(str -> {
            System.out.println(str);
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
         /*
           Map相关
           forEach , getOrDefault() , putIfAbsent() , remove() , replace() , replaceAll() , merge() , compute(),computeIfAbsent(), computeIfAbsent()
         */
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.forEach((key, value) ->
                System.out.println(key + "=" + value)
        );
        System.out.println(map.getOrDefault(3, "xiaoyang"));
        System.out.println(map.getOrDefault(5, "xiaoyang"));
    }


}
