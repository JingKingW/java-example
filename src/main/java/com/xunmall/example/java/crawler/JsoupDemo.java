package com.xunmall.example.java.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHCL on 2018/8/3.
 */
public class JsoupDemo {

    //从字符串中获取
    public static void getParByString() {
        String html = "<html><head><title> 这里是字符串内容</title></head\"+ \">\"+\"<body><p class='p1'> 这里是 jsoup 作用的相关演示</p></body></html>\"";
        Document document = Jsoup.parse(html);
        Elements links = document.select("p[class]");
        for (Element link : links) {
            String linkClass = link.className();
            String linkText = link.text();
            System.out.println(linkClass);
            System.out.println(linkText);
        }
    }

    //从本地文件中获取
    public static void getHerByLocal() {
        String filePaht = "d:\\test.html";
        File file = new File(filePaht);
        Document document = null;
        try {
            document = Jsoup.parse(file, "UTF-8", "www.baidu.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.select("a[href]");
        for (Element element : elements) {
            String href = element.attr("href");
            String text = element.text();
            System.out.println(href + ": " + text);
        }
    }

    public static Map<String, String> getHrefByNet(String url) {
        Map<String, String> map = new HashMap<>();
        try {
            Document document = Jsoup.connect(url).get();
            System.out.println(document.title());
            Elements elements = document.select("a[href]");
            for (Element element : elements) {
                String linkHref = element.attr("abs:href");
                String linkText = element.text();
                map.put(linkText, linkHref);
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("加载失败", "error");
        }

        return map;
    }


    public static void main(String[] args) {
        //getParByString();
        //getHerByLocal();
        String url1 = "https://movie.douban.com/";
        String url2 = "http://maoyan.com/";
        String url3 = "http://www.dytt8.net/";
        Map<String,String> result = getHrefByNet(url3);
        if(!result.isEmpty()){
            result.forEach((key,value)->
                    System.out.println(key+": "+ value)
            );
        }
    }
}
