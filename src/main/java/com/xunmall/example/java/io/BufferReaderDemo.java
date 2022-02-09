package com.xunmall.example.java.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by yanjing_wang on 2017/5/28 0028.
 */
public class BufferReaderDemo {
        public static void main(String[] args) throws Exception{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            boolean flag = true;
            while(flag){
                System.out.println("请输入您的年龄：");
                String str = bufferedReader.readLine();
                if(str.matches("\\d+")){
                    System.out.println("年龄是：" + Integer.parseInt(str));
                    flag = false;
                }else{
                    System.out.println("输入年龄格式不正确！，请重新输入");
                }
            }

        }

}
