package com.xunmall.example.java.httpclient;

import com.xunmall.example.java.json.FastJsonUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WangYanjing
 * @Date: 2019/6/21 13:38
 * @Description:
 */
public class SimpleHttpClientDemo {
    private static String outUrl = "https://agw.y5ops.com";
    private static String innerUrl = "https://aops-api-agw.internal.zenmen.com";
    private static String apiKey = "6621f6f87b44a8bc32a023df7faa2fcf";

    public static void main(String[] args) throws IOException, URISyntaxException {
        //String callid = sendPhoneByPhone("15021618549");
        queryMobileStatus("8426760a-9911-11ec-b718-5254002ab735");

    }

    private static void sendPhoneByUser(String userName) throws IOException, URISyntaxException {
        String sendApiUser = "/api/cmsi/sendVoiceToUser";
        Map<String, Object> param = new HashMap<>();
        param.put("templateId", 504848);
        param.put("user", userName);

        String params = FastJsonUtil.convertObjectToJSON(param);

        Map<String, String> heads = new HashMap<>();
        heads.put("Content-Type", "application/json");
        heads.put("apiKey", apiKey);

        String forStringWihtHead = HttpSendUtils.doPostForStringWithHead(outUrl + sendApiUser, params, heads);
        System.out.println(forStringWihtHead);

    }

    private static String sendPhoneByPhone(String phone) throws IOException, URISyntaxException {
        String sendApiPhone = "/api/cmsi/sendVoice";
        Map<String, Object> param = new HashMap<>();
        param.put("templateID", 504848);
        param.put("mobile", phone);

        String params = FastJsonUtil.convertObjectToJSON(param);

        Map<String, String> heads = new HashMap<>();
        heads.put("Content-Type", "application/json");
        heads.put("apiKey", apiKey);

        String forStringWithHead = HttpSendUtils.doPostForStringWithHead(outUrl + sendApiPhone, params, heads);

        System.out.println(forStringWithHead);

        Map<String,String> result = FastJsonUtil.toBean(forStringWithHead,Map.class);

        return result.get("data");
    }

    private static void sendCallBack(String phone) throws IOException, URISyntaxException {
        String sendCallBack = "/api/cmsi/sendVoiceCallback";
        Map<String, String> param = new HashMap<>();
        param.put("templateID", "504848");
        param.put("mobile", phone);


        String params = FastJsonUtil.convertObjectToJSON(param);

        Map<String, String> heads = new HashMap<>();
        heads.put("Content-Type", "application/json");
        heads.put("apiKey", apiKey);

        String forStringWihtHead = HttpSendUtils.doPostForStringWithHead(outUrl + sendCallBack, params, heads);
        System.out.println(forStringWihtHead);

    }

    private static void queryMobileStatus(String callCode) throws IOException, URISyntaxException {
        String callPhone = "/api/cmsi/voice/queryMobileStatus";
        Map<String, String> param = new HashMap<>();
        param.put("callid", callCode);


        String params = FastJsonUtil.convertObjectToJSON(param);

        Map<String, String> heads = new HashMap<>();
        heads.put("Content-Type", "application/json");
        heads.put("apiKey", apiKey);

        String forStringWihtHead = HttpSendUtils.doPostForStringWithHead(outUrl + callPhone, params, heads);
        System.out.println(forStringWihtHead);

    }


}
