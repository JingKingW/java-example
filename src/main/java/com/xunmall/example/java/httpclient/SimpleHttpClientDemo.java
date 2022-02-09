package com.xunmall.example.java.httpclient;

/**
 * @Author: WangYanjing
 * @Date: 2019/6/21 13:38
 * @Description:
 */
public class SimpleHttpClientDemo {
    public static void main(String[] args) {
        try {
            int result = HttpSendUtils.commonGet("http://www.krise.xyz" , null).getStatusLine().getStatusCode();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
