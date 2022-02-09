package com.xunmall.example.java.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SHCL on 2018/8/3.
 */
public class BookCrallerDemo {

    public static String getBookHtml(String url) {
        URL u;
        HttpURLConnection httpURLConnection;
        String result = "";
        try {
            u = new URL(url);
            try {
                httpURLConnection = (HttpURLConnection) u.openConnection();
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                    String read;
                    while ((read = bufferedReader.readLine()) != null) {
                        result += read;
                        result += "\r\n";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> getBookMenu(String read) {
        List<String> result = new ArrayList<>();
        Document document = Jsoup.parse(read);
        Elements elements = document.select("a");
        for (Element element : elements) {
            String href = element.attr("href");
            String regex = "/categories(.*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(href);
            while (matcher.find()) {
                if (!result.contains(matcher.group())) {
                    result.add(href);
                }
            }
        }
        return result;
    }

    public static List<String> getBookItem(String read) {
        List<String> arrayList = new ArrayList<>();
        String con = "<a href=(.*)<img src=\"/images/download";
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while (mr.find()) {
            if (!arrayList.contains(mr.group())) {
                arrayList.add(mr.group());
            }
        }
        return arrayList;
    }

    public static void find(String read) {
        String con = "<a href=\"(.*)pan.baidu.com(.*)ref";
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while (mr.find()) {
            String[] bookPan = mr.group().split("\"");
            String bookM = bookPan[1];
            System.out.print(bookM + "        ");

        }
    }

    public static void getM(String read) {
        String con = "密码(.*)";
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while (mr.find()) {
            System.out.print(mr.group());
        }
        System.out.println();
    }


    public static void main(String[] args) {
        List<String> list = getBookMenu(getBookHtml("http://bestcbooks.com/"));
        for (String url : list) {
            //读取到详细页面
            String read = getBookHtml("http://bestcbooks.com" + url);
            List<String> bookItem = getBookItem(read);
            for (int j = 0; j < bookItem.size(); j++) {
                String[] bookIn = bookItem.get(j).split("\"");
                String myBook = bookIn[1];
                String myBookCode = getBookHtml("http://bestcbooks.com" + myBook);
                find(myBookCode);
                getM(myBookCode);
            }
        }

    }

    @Test
    public void testHtml() {
        String div = "      <a href=\"/films/1216365\" target=\"_blank\" data-act=\"movie-click\" data-val=\"{movieid:1216365}\">\n" +
                "      <div class=\"movie-poster\">\n" +
                "        <img class=\"poster-default\" src=\"//ms0.meituan.net/mywww/image/loading_2.e3d934bf.png\" />\n" +
                "        <img data-src=\"http://p0.meituan.net/movie/d2d0c2774fca5a3de1b7e6b7fc0a74cf4260325.jpg@160w_220h_1e_1c\" />\n" +
                "      </div>\n" +
                "      </a>\n" +
                "        <div class=\"channel-action channel-action-sale\">\n" +
                "  <a>购票</a>\n" +
                "</div>\n" +
                "\n" +
                "      <div class=\"movie-ver\"></div>\n" +
                "    </div>\n" +
                "    <div class=\"channel-detail movie-item-title\" title=\"小偷家族\">\n" +
                "      <a href=\"/films/1216365\" target=\"_blank\" data-act=\"movies-click\" data-val=\"{movieId:1216365}\">小偷家族</a>\n" +
                "    </div>\n" +
                "<div class=\"channel-detail channel-detail-orange\"><i class=\"integer\">8.</i><i class=\"fraction\">1</i></div>\n" +
                "  \n" +
                "  <dd>";

        Document document = Jsoup.parse(div);
        Elements elements = document.select("a");
        List<String> result = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for(Element element : elements){
            String href = element.attr("href");
            String regex = "/films(.*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(href);
            while (matcher.find()) {
                if (!result.contains(matcher.group())) {
                    String value = matcher.group();
                    String fist = document.select(".channel-detail.channel-detail-orange").select(".integer").text();
                    String end = document.select(".channel-detail.channel-detail-orange").select(".fraction").text();
                    result.add(value);
                    System.out.println(value+":  "+(fist+end));
                    map.put(value, value);
                }
            }
        }

    }


}
