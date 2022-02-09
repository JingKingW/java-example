package com.xunmall.example.java.spi;

import java.util.ServiceLoader;

/**
 * @Author: wangyj03
 * @Date: 2021/10/19 20:15
 */
public class MainApp {
    public static void main(String[] args) {
        ServiceLoader<PrintService> serviceServiceLoader = ServiceLoader.load(PrintService.class);
        for (PrintService printService : serviceServiceLoader){
            printService.printInfo();
        }
    }
}
