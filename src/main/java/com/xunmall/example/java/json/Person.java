package com.xunmall.example.java.json;

import lombok.Data;

import java.util.List;

/**
 * Created by wangyanjing on 2018/10/8.
 */
@Data
public class Person {
    private String name;
    private int age;
    private List<Student> manList;
    private Student man;
}
