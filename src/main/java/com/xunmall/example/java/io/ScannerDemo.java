package com.xunmall.example.java.io;

import java.util.Scanner;


/**
 * Created by yanjing_wang on 2017/5/28 0028.
 */
public class ScannerDemo {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        System.out.println("请输入数据：");
        if (scanner.hasNextInt()){
            System.out.println("数据为：" + scanner.nextInt());
        }else{
            System.out.println("输入的格式不对");
        }
        scanner.close();
    }
}
