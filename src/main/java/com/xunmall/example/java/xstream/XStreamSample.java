package com.xunmall.example.java.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by wangyanjing on 2018/11/28.
 */
public class XStreamSample {

    private static XStream xStream;

    static {
        //创建一个实例并指定一个xml解析器
        xStream = new XStream(new DomDriver());
        //设置别名和属性
        xStream.alias("loginLog",LoginLog.class);
        xStream.alias("user",User.class);

        xStream.aliasField("id",User.class,"userId");

        xStream.aliasAttribute(LoginLog.class,"userId","id");
        xStream.useAttributeFor(LoginLog.class,"userId");

        xStream.addImplicitCollection(User.class,"logs");
        xStream.registerConverter(new DateConverter(new Locale("zh","CN")));
    }

    public static User getUser() {
        LoginLog loginLog = new LoginLog();
        loginLog.setIp("192.168.1.112");
        loginLog.setLoginDate(new Date());
        List logs = new ArrayList();
        logs.add(loginLog);
        User user = new User();
        user.setUserId(1);
        user.setUserName("jeremy");
        user.setLogs(logs);
        return user;
    }

    public static void objectToXML() throws FileNotFoundException {

        User user = getUser();

        FileOutputStream fileOutputStream = new FileOutputStream("d:/XStreamSample.xml");

        xStream.toXML(user, fileOutputStream);
    }

    public static void XMLToObject() throws FileNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("d:/XStreamSample.xml");

        User user = (User) xStream.fromXML(fileInputStream);

        for (Object log : user.getLogs()) {
            if (log != null && log instanceof LoginLog) {
                LoginLog item = (LoginLog) log;
                System.out.println("访问ip" + item.getIp());
                System.out.println("访问时间" + item.getLoginDate());
            }
        }

    }

    public static void main(String[] args) {
        try {
            objectToXML();
            XMLToObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
