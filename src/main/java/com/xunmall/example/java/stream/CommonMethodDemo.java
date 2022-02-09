package com.xunmall.example.java.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yanjing_wang on 2017/5/29 0029.
 */
public class CommonMethodDemo {
    public static void main(String[] args) throws Exception {
        /**
         * stream方法使用
         * forEach() , filter() , distinct() , map() , flatMap()
         */
        List<String> all = new ArrayList<String>();
        all.add("hello");
        all.add("world");
        all.add("java");
        all.add("javascript");
        all.add("php");
        all.add("redis");
        all.add("memcached");
        Stream stream = all.stream().map(x -> x.toUpperCase()).filter(x -> x.contains("J"));
        Iterator iterator = stream.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        List<String> list = all.stream().skip(2).limit(2).collect(Collectors.toList());
        System.out.println(list);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        /**
         * reduce 常见用法
         */

        Stream<String> string = Stream.of("i", "love", "yaobingyan", "chengdu");
        Optional<String> optional = string.reduce((param1, param2) -> param1.length() >= param2.length() ? param1 : param2);
        System.out.println(optional.get());
        Stream<String> string2 = Stream.of("i", "love", "yaobingyan", "chengdu");
        Integer sumLength = string2.reduce(0, (sum, count) -> sum + count.length(), (a, b) -> a + b);
        System.out.println(sumLength);


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        /**
         * collect 常见用法 生成常用集合
         */
        Stream<String> string3 = Stream.of("i", "love", "yaobingyan", "chengdu");
        List<String> stringList = string3.collect(Collectors.toList());
        System.out.println(stringList.size());
        Stream<String> string4 = Stream.of("i", "love", "yaobingyan", "chengdu", "beijing");
        List<String> stringList1 = string4.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(stringList1.size());
        Stream<String> string5 = Stream.of("i", "love", "yaobingyan", "chengdu", "beijing", "shanghai");
        HashSet<String> hashSet = string5.collect(Collectors.toCollection(HashSet::new));
        System.out.println(hashSet.size());

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        /**
         * collect 常见用法 生成Map
         */
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "jake", "ABC123", "SAHNGHAI", 23));
        studentList.add(new Student(2L, "sande", "dfr123", "SAHNGHAI", 24));
        studentList.add(new Student(3L, "bool", "ABC1dfa23", "SAHNGHAI", 43));
        studentList.add(new Student(4L, "ary", "ABC1ss23", "SAHNGHAI", 13));
        studentList.add(new Student(5L, "owen", "ABC1dfsd23", "SAHNGHAI", 24));
        studentList.add(new Student(6L, "Tom", "ABC12sd3", "SAHNGHAI", 25));

        Map<String, List<Student>> byDept = studentList.stream().collect(Collectors.groupingBy(Student::getAddress));
        byDept.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        List<Student> studentList2 = new ArrayList<>();
        studentList2.add(new Student(1L, "jake", "ABC123", "SAHNGHAI", 23));
        studentList2.add(new Student(2L, "sande", "dfr123", "SAHNGHAI", 24));
        studentList2.add(new Student(3L, "bool", "ABC1dfa23", "SAHNGHAI", 43));
        studentList2.add(new Student(4L, "ary", "ABC1ss23", "SAHNGHAI", 13));
        studentList2.add(new Student(5L, "owen", "ABC1dfsd23", "SAHNGHAI", 24));
        studentList2.add(new Student(6L, "Tom", "ABC12sd3", "SAHNGHAI", 25));
        Map<String,Integer> result = studentList2.stream().collect(Collectors.groupingBy(Student::getAddress,Collectors.summingInt(Student::getAge)));
        result.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<String> strings = new ArrayList<>(Arrays.asList("i", "love", "shanghai", "and", "huaian"));
        String resultString = strings.stream().collect(Collectors.joining(",","{","}"));
        System.out.println(resultString);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<String> strings2 = new ArrayList<>(Arrays.asList("i", "love", "shanghai", "and", "huaian"));
        System.out.println(strings2.stream().findFirst().get());
    }


}
