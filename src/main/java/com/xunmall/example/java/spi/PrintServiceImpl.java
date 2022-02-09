package com.xunmall.example.java.spi;

/**
 * @Author: wangyj03
 * @Date: 2021/10/19 20:13
 */
public class PrintServiceImpl implements PrintService {
    @Override
    public void printInfo() {
        System.out.println("Hello World");
    }
}
