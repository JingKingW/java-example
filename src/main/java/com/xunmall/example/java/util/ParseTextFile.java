package com.xunmall.example.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangyj03
 * @Date: 2021/10/13 11:30
 */
public class ParseTextFile {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> serverMap = new HashMap<>();
        String file = "D:\\opensource\\example-all\\java-example\\src\\main\\resources\\1.log";
        FileInputStream fis = null;
        RandomAccessFile raf = null;
        try {
            fis = new FileInputStream(file);
            raf = new RandomAccessFile(new File(file), "r");
            String s;
            while ((s = raf.readLine()) != null) {
                if (s.contains("short.lianxinapp.com")) {
                    String newStr = s.substring(22, s.length());
                    String[] split = newStr.split("/");
                    String key = split[0];
                    if (serverMap.containsKey(key)) {
                        List<String> strings = serverMap.get(key);
                        strings.add(newStr);
                        serverMap.put(key, strings);
                    } else {
                        List<String> valueList = new ArrayList<>();
                        valueList.add(newStr);
                        serverMap.put(key, valueList);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        System.out.println(serverMap.size());
        for (Map.Entry<String,List<String>> entry : serverMap.entrySet()){
            System.out.println("服务名称: " + entry.getKey() + "  对应的接口数量: " + entry.getValue().size() + " 样例： " + entry.getValue().toString());
        }

        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }
}
