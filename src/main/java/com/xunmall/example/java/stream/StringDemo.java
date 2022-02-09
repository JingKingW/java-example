package com.xunmall.example.java.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Gimgoog on 2017/12/12.
 */
public class StringDemo {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("投资满足2000元" + ":" + "获得" + 3 + "个普通彩蛋");
        stringList.add("第一位投资用户" + ":" + "获得1个普通彩蛋");
        stringList.add("最后一位投资用户" + ":" + "获得1个普通彩蛋");
        String joined = stringList.stream().collect(Collectors.joining(" , ", "{", "}"));
        System.out.println(joined);
        String string = "sign_1";
        System.out.println(string.contains("sign"));

    }
}
