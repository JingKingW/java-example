package com.xunmall.example.java.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyanjing on 2018/9/18.
 */
public class RegexExample {

    @Test
    public void testSimple() {

        String content = "I am need " + " from facebook.com";

        String pattern = ".*need.*";

        boolean match = Pattern.matches(pattern, content);

        System.out.println("字符串是否包含'need' " + match);
    }

    @Test
    public void testString() {
        // 匹配出3个字符的字符串
        String str = "abc 124 ewqeq qeqe   qeqe   qeqe  aaaa  fs fsdfs d    sf sf sf  sf sfada dss dee ad a f s f sa a'lfsd;'l";
        Pattern pt = Pattern.compile("\\b\\w{3}\\b");
        Matcher match = pt.matcher(str);
        while (match.find()) {
            System.out.println(match.group());
        }
        // 匹配出邮箱地址
        String str2 = "dadaadad   da da   dasK[PWEOO-123- DASJAD@DHSJK.COM DADA@DAD.CN =0KFPOS9IR23J0IS ADHAJ@565@ADA.COM.CN shuqi@162.com UFSFJSFI-SI- ";
        Pattern pet2 = Pattern.compile("\\b\\w+@\\w+(\\.\\w{2,4})+\\b");
        Matcher match2 = pet2.matcher(str2);
        System.out.println(match2.groupCount());
        while (match2.find()) {
            System.out.println(match2.group());
        }
    }

    @Test
    public void testArray() {
        String sr = "dada ada adad adsda ad asdda adr3 fas daf fas fdsf 234 adda";
        //包含两个匹配组，一个是三个字符，一个是匹配四个字符
        Pattern pet = Pattern.compile("\\b(\\w{3}) *(\\w{4})\\b");
        Matcher match = pet.matcher(sr);
        int countAll = match.groupCount();//2
        while (match.find()) {
            System.out.print("匹配组结果：");
            for (int i = 0; i < countAll; i++) {
                System.out.print(String.format("\n\t第%s组的结果是:%s", i + 1, match.group(i + 1)));
            }
            System.out.print("\n匹配的整个结果:");
            System.out.println(match.group());
        }
    }

    //非零开头的最多带两位小数的数字^([1-9][0-9]*)+(\.\d{1,2})?$
    //有两位小数的正实数 ^[1-9]+(\.\d{2})?$
    //英文和数字^[A-Za-z0-9]+$
    //Email地址^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
    //InternetURL[a-zA-z]+://[^\s]* 或 ^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
    //身份证号(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)
    //强密码 ^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$
    //腾讯QQ号 [1-9][0-9]{4,}
    @Test
    public void testRemeberMe(){
        String content = "  ";



    }

    @Test
    public void regSupperIp(){
        String ip = "10.204.40.239";
        String host ="ladfasdfasdf-pre";
        String regStr = "(10\\.204\\.(31|32|36|37|40)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d))|([\\s\\S]*-pre[\\s\\S]*)";
        boolean matches = ip.matches(regStr);
        System.out.println(matches);

        boolean matches2 = host.matches(regStr);
        System.out.println(matches2);
    }



}
