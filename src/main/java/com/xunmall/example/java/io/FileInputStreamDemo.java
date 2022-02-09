package com.xunmall.example.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class FileInputStreamDemo {

    public static void main(String[] args) throws Exception {
        File file = new File("d:" + File.separator + "text" + File.separator + "aa.txt");

        InputStream inputStream = new FileInputStream(file);

        /*byte[] data = new byte[1024];

        int len = (int)inputStream.read(data);*/

        int temp = 0;
        int foot = 0;
        byte[] data = new byte[1024];

        while((temp=inputStream.read()) != -1){
            data[foot++] = (byte)temp;
        }

        System.out.println("读取的数据：[" + new String(data,0,foot) + "]");

        inputStream.close();
    }


}
