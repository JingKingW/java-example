package com.xunmall.example.java.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyanjing on 2018/10/8.
 */
public class GsonUtilTest {

    public static void main(String[] args) {
        // map集合转化成json
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "张三");
        map.put("age", "20");
        map.put("sex", "man");
        String mapString = GsonUtil.parseToJson(map);
        System.out.println(mapString);
        // json转化成map，list集合类似
        Gson gson = new Gson();
        Map resultMap = gson.fromJson(mapString, new TypeToken<Map<String, String>>() {
        }.getType());
        System.out.println("json转换map后的数据是" + resultMap.get("name") + " " + resultMap.get("age") + " " + resultMap.get("sex"));
        // 实体对象中单条数据解析
        String str1 = "{'name':'zhangsan1','age':11}";
        Person p1 = GsonUtil.parseJsonWithClass(str1, Person.class);
        System.out.println(p1.getName() + " " + p1.getAge());
        // 实体中单条数据有嵌套数据
        String str2 = "{'name':'zhangsan2','age':22,'man':{'name':'lisi','age':12}}";
        Person p2 = GsonUtil.parseJsonWithClass(str2, Person.class);
        System.out.println(p2.getName() + " " + p2.getAge() + " " + p2.getMan().toString());
        // 实体中解析集合数据，多条json数据
        String str = "[{'name':'John','age':20, 'man':{'name':'zhangsan3','age':33},'manList':[{'name':'English','age':100},{'name':'Math','age':78},{'name':'shuxue','age':98}]},{'name':'jack','age':33,'man':    {'name':'zhangsan4','age':34},'manList':[{'name':'1111','age':33},{'name':'21213','age':55}]}]";
        List<Person> pp = GsonUtil.parseJsonArrayWithGson(str, Person.class);
        System.out.println("从json转换后的数据:");

        String strMenu = "";
    }
}
