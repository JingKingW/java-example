package com.xunmall.example.java.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class FileOutputStreamDemo {

    public static void main(String[] agrs) throws Exception{
        File file = new File("d:"+ File.separator+"text" +File.separator +"aa.txt");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        OutputStream outputStream = new FileOutputStream(file);

        String data = "最幸福的事情莫过于，自己喜欢的女孩也刚好喜欢自己！";

        outputStream.write(data.getBytes());

        outputStream.close();

    }







}
