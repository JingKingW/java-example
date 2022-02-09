package com.xunmall.example.java.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyanjing on 2018/10/10.
 */
public class VideoJsonDemo {

    public static void main(String[] args) throws IOException {
        String vodie = convertToString("/video.json");
        JSONObject jsonObject = JSON.parseObject(vodie);
        JSONObject data = jsonObject.getJSONObject("data");
        for (Map.Entry object : data.entrySet()) {
            String key = (String) object.getKey();
            JSONObject value = (JSONObject) object.getValue();
            String name = value.getString("name");
            String playSource = value.getString("playSource");
            if (!StringUtils.isBlank(playSource)) {
                Map<String, String> stringMap = new HashMap<>();
                JSONArray jsonArray = JSON.parseArray(playSource);
                jsonArray.forEach(item -> {
                    JSONObject obj = (JSONObject) item;
                    String source = obj.getString("sourceWebsite");
                    String url = obj.getString("playUrl");
                    stringMap.put(source, url);
                });
                System.out.println(stringMap.toString());
            }
            System.out.println(key + "  " + name + "  " + playSource);
        }
    }


    public static String convertToString(String path) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(path);
        InputStream inputStream = classPathResource.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        byte[] data = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(data)) != -1) {
            stringBuilder.append(new String(data, 0, len));
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}
