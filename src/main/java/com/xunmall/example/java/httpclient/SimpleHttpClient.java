package com.xunmall.example.java.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gimgoog
 * @date 2018/5/30
 */
public class SimpleHttpClient {

    public static void main(String[] args) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mobile", "15021618549"));
        params.add(new BasicNameValuePair("password", "57f231b1ec41dc6641270cb09a56f897"));
        HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(30000).setConnectionRequestTimeout(20000).build();
        HttpClient httpClient = HttpClientBuilder.create().build();

        String url = "https://ceshiopen.wanggejinfu.com.cn/service/dubbo/member/login.do";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(httpEntity);
        httpPost.setConfig(requestConfig);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity result = httpResponse.getEntity();
            System.out.println(EntityUtils.toString(result));
        } else {
            System.out.println("请求失败");
        }
    }


}
