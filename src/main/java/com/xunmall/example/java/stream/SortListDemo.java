package com.xunmall.example.java.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: WangYanjing
 * @Date: 2019/6/3 15:02
 * @Description:
 */
public class SortListDemo {

    public static void main(String[] args) {

        List<Student> list = new ArrayList<>();
        list.add(new Student(1L, "jake", "ABC123", "SAHNGHAI", 23));
        list.add(new Student(2L, "sande", "dfr123", "SAHNGHAI", 24));
        list.add(new Student(3L, "bool", "ABC1dfa23", "SAHNGHAI", 43));

        System.out.println("--------Natural Sorting by Username---------");
        List<Student> slist = list.stream().sorted().collect(Collectors.toList());
        slist.forEach(item -> {
            System.out.println(item.toString());
        });

        System.out.println("---------Natural Sorting by Username in reverse order---------------");
        slist = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        slist.forEach(item -> {
            System.out.println(item.toString());
        });

        System.out.println("---Sorting using Comparator by Age---");
        slist = list.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        slist.forEach(item -> {
            System.out.println(item.toString());
        });

        System.out.println("---Sorting using Comparator by Age with reverse order---");
        slist = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        slist.forEach(item -> {
            System.out.println(item.toString());
        });

    }
}
