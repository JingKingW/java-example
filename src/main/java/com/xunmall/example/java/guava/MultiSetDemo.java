package com.xunmall.example.java.guava;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

/**
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class MultiSetDemo {
    public static void main(String[] args) {
        Multiset<String> set= LinkedHashMultiset.create();
        set.add("a");
        set.add("a");
        set.add("a");
        set.add("a");
        // 添加或删除指定元素使其在集合中的数量是count
        set.setCount("a",5);
        // 给定元素在Multiset中的计数
        System.out.println(set.count("a"));
        System.out.println(set);
        // 所有元素计数的总和,包括重复元素
        System.out.println(set.size());
        // 所有元素计数的总和,不包括重复元素
        System.out.println(set.elementSet().size());
        // 清空集合
        set.clear();
        System.out.println(set);

    }
}
