package com.xunmall.example.java.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gimgoog on 2018/8/4.
 */
public class BookCrawler {

    public static String getBookNetByURL(String url) {
        URL u;
        HttpURLConnection httpURLConnection;
        String result = "";
        try {
            u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            if (httpURLConnection.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String read;
                while ((read = bufferedReader.readLine()) != null) {
                    result += read;
                    result += "\r\n";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List getBookString(String value) {
        List<String> result = new ArrayList<>();
        Document document = Jsoup.parse(value);
        Elements elements = document.select("a");
        for (Element link : elements) {
            String hrefValue = link.attr("href");
            String textValue = link.text();
            if (!result.contains(hrefValue)) {
                result.add(hrefValue + ": " + textValue);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        List<String> list = getBookString(getBookNetByURL("http://bestcbooks.com/"));
        list.forEach(item -> {
            System.out.println(item);
        });

    }


}
