package com.xunmall.example.java.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by yanjing_wang on 2017/5/28 0028.
 */
public class ByteArrayDemo {

    public static void main(String[] args) throws Exception{
        String str = " Hello World ! Very Good .";
        InputStream input = new ByteArrayInputStream(str.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        int temp = 0;
        while((temp = input.read())!=-1){
            output.write(Character.toLowerCase(temp));
        }
        System.out.println(output);
        input.close();
        output.close();
    }





}
