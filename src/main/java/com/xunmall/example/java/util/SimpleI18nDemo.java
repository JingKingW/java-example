package com.xunmall.example.java.util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by wangyanjing on 2018/10/18.
 */
public class SimpleI18nDemo {

    @Test
    public void testNumber() {
        Locale local = new Locale("zh", "CN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(local);
        double amt = 1231231.123;
        System.out.println(numberFormat.format(amt));
    }

    @Test
    public void testDate() {
        Locale locale = new Locale("zh", "CN");
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        System.out.println(dateFormat.format(date));
    }

    @Test
    public void testMessage() {
        Locale loc = new Locale("en","US");
        ResourceBundle rb = ResourceBundle.getBundle("message", loc);
        System.out.println(rb.getString("hello"));
    }

    @Test
    public void testMessageFormat() {
        String pattern1 = "{0},你好！ 你于{1}在工商银行存入{2}元";
        String pattern2 = "At {1,time,short} On {1,date,long}, {0} paid {2,number,currency} .";

        Object[] params = {"John", new GregorianCalendar().getTime(), 1.0E3};

        String msg1  = MessageFormat.format(pattern1,params);

        MessageFormat mf = new MessageFormat(pattern2,Locale.US);
        String msg2 = mf.format(params);

        System.out.println(msg1);
        System.out.println(msg2);

    }


}
