package com.xunmall.example.java.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Gimgoog
 * @date 2018/5/30
 */
@Slf4j
public class HttpSendUtils {

    public static String defaultEncoding = "utf-8";

    private static List<NameValuePair> paramsConverter(Map<String, String> params) {
        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Map.Entry<String, String>> paramsSet = params.entrySet();
        for (Map.Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        return nvps;
    }

    public static String doGetForString(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet gm = new HttpGet();
        URIBuilder builder = new URIBuilder(url);
        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(paramsConverter(queryParams));
        }
        gm.setURI(builder.build());
        HttpResponse httpResponse = HttpClientTool.getHttpClient().execute(gm);
        return readStream(httpResponse.getEntity().getContent(), defaultEncoding);
    }

    public static HttpResponse doGetForResponse(String url, Map<String, String> queryParams)
            throws URISyntaxException, ClientProtocolException, IOException {
        HttpGet gm = new HttpGet();
        URIBuilder builder = new URIBuilder(url);
        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(paramsConverter(queryParams));
        }
        gm.setURI(builder.build());
        return HttpClientTool.getHttpClient().execute(gm);
    }

    public static String doPostForString(String url, Map<String, String> queryParams, Map<String, String> formParams)
            throws URISyntaxException, IOException {
        HttpPost pm = new HttpPost();
        URIBuilder builder = new URIBuilder(url);
        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(paramsConverter(queryParams));
        }
        pm.setURI(builder.build());
        // 填入表单参数
        if (formParams != null && !formParams.isEmpty()) {
            pm.setEntity(new UrlEncodedFormEntity(paramsConverter(formParams), defaultEncoding));
        }
        HttpResponse httpResponse = HttpClientTool.getHttpClient().execute(pm);
        return readStream(httpResponse.getEntity().getContent(), defaultEncoding);
    }

    public static String readStream(InputStream in, String encoding) {
        String returnParams = "";
        if (in == null) {
            return null;
        }
        try {
            InputStreamReader inReader = null;
            if (encoding == null) {
                inReader = new InputStreamReader(in, defaultEncoding);
            } else {
                inReader = new InputStreamReader(in, encoding);
            }
            char[] buffer = new char[1024];
            int readLen = 0;
            StringBuffer sb = new StringBuffer();
            while ((readLen = inReader.read(buffer)) != -1) {
                sb.append(buffer, 0, readLen);
            }
            inReader.close();
            log.info("同步返回的信息：{}", sb.toString());
            returnParams = sb.toString();
            return returnParams;
        } catch (Exception e) {
            log.error("读取返回内容出错", e);
        }
        return null;
    }

    public static HttpResponse commonGet(String url, Map<String, String> queryParams) throws IOException, URISyntaxException {
        HttpGet gm = new HttpGet();
        URIBuilder builder = new URIBuilder(url);
        // 填入查询参数
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.setParameters(paramsConverter(queryParams));
        }
        gm.setURI(builder.build());
        return HttpClientTool.getHttpClient().execute(gm);
    }
}
