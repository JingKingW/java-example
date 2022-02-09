package com.xunmall.example.java.json;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JacksonUtilTest {


    public static void main(String[] args) throws ParseException, IOException {
        beanTojack();
        jsonTobean();
    }

    public static void beanTojack() throws ParseException, IOException {

        Student student = new Student();
        student.setName("curry");
        student.setAge(30);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        student.setDate(dateFormat.parse("1988-9-21"));
        student.setSex("M");

        Student student2 = new Student();
        student2.setName("amy");
        student2.setAge(21);
        student2.setDate(dateFormat.parse("1988-10-21"));
        student2.setSex("G");

        List<Student> users = new ArrayList<>();
        users.add(student);
        users.add(student2);

        //对象转json格式
        String beanToJson = JacksonUtil.BeanToJson(users);
        System.out.println(beanToJson);
    }

    public static void jsonTobean() throws JsonParseException, JsonMappingException, IOException {
        String json = "{\"name\":\"curry\",\"age\":30,\"sex\":\"M\",\"date\":590774400000}";

        Student jsonToBean = JacksonUtil.JsonToBean(json, Student.class);
        System.out.println(jsonToBean);
    }

}
