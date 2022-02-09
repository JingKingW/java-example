package com.xunmall.example.java.guava;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class CollectionsDemo {
    public static void main(String[] args) {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Integer> set2 = Sets.newHashSet(3, 4, 5, 6);
        // 交集
        Sets.SetView<Integer> inter = Sets.intersection(set1, set2);
        System.out.println(inter);
        // 差集,在A中不在B中
        Sets.SetView<Integer> diff = Sets.difference(set1, set2);
        System.out.println(diff);
        // 并集
        Sets.SetView<Integer> union = Sets.union(set1, set2);
        System.out.println(union);
    }
}
