package com.xunmall.example.java.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * Created by wangyanjing on 2018/11/29.
 */
public class XStreamJSONSample {
    private static XStream xStream;

    public static void toJsonByJettisonMapperXmlDriver() throws FileNotFoundException {
        User user = XStreamSample.getUser();
        FileOutputStream fileOutputStream = new FileOutputStream("d:/jettisonMapperSample.josn");
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, Charset.forName("UTF-8"));
        xStream = new XStream(new JettisonMappedXmlDriver());
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.alias("user", User.class);
        xStream.toXML(user, writer);
    }

    public static void toJsonByJsonHierarchicalStreamDriver() throws FileNotFoundException {
        User user = XStreamSample.getUser();
        FileOutputStream fileOutputStream = new FileOutputStream("d:/jsonByHierarchialSample.json");
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, Charset.forName("UTF-8"));
        xStream = new XStream(new JsonHierarchicalStreamDriver());
        xStream.alias("user", User.class);
        xStream.toXML(user, writer);
    }

    public static void main(String[] args) {
        try {
            toJsonByJettisonMapperXmlDriver();
            toJsonByJsonHierarchicalStreamDriver();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
