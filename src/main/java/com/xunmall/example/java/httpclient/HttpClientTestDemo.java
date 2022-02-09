package com.xunmall.example.java.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Gimgoog
 * @date 2018/5/30
 */
public class HttpClientTestDemo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxcheckurl";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String location = null;
        int responseCode = 0;
        try {
            final HttpGet request = new HttpGet(url);
            org.apache.http.params.HttpParams params = new BasicHttpParams();
            params.setParameter("requrl", "http://www.baidu.com/");
            params.setParameter("skey", "@crypt_1393bae8_964724eaac4e1355ad4040eebdbfd7ef");
            params.setParameter("deviceid", "e648239596923075");
            params.setParameter("pass_ticket", "undefined");
            params.setParameter("opcode", "2");
            params.setParameter("scene", "1");
            params.setParameter("username", "@16ddd97f2ba4bade0138329e40ca8eb6");
            request.setParams(params);
            HttpResponse response = httpclient.execute(request);
            responseCode = response.getStatusLine().getStatusCode();
            System.out.println(responseCode);
            if (responseCode == 200) {
                System.out.println(HttpSendUtils.readStream(response.getEntity().getContent(), "utf-8"));
            } else if (responseCode == 301) {
                org.apache.http.Header locationHeader = response.getFirstHeader("Location");
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    System.out.println(location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
