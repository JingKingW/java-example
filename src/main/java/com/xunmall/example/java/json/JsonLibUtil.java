package com.xunmall.example.java.json;

import net.sf.json.JSONObject;

/**
 * @Author: WangYanjing
 * @Date: 2019/6/17 17:13
 * @Description:
 */
public class JsonLibUtil {
    public static String bean2Json(Object obj) {
        JSONObject jsonObject = JSONObject.fromObject(obj);
        return jsonObject.toString();
    }


    @SuppressWarnings("unchecked")
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return (T) JSONObject.toBean(JSONObject.fromObject(jsonStr), objClass);
    }
}
