package com.xunmall.example.java.concurrent.lock;

import java.util.concurrent.TimeUnit;


/**
 * @Author: WangYanjing
 * @Date: 2019/1/8 9:42
 * @Description: 对静态方法的修饰可以和实例方法的修饰同时使用，不会阻塞，因为一个是修饰的Class类，一个是修饰的实例对象
 */
public class SynchronizedTest {

    public static synchronized void StaticSyncTest() {
        for (int i = 0; i < 3; i++) {
            System.out.println("StaticSyncTest");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public synchronized void NonStaticSyncTest() {
        for (int i = 0; i < 3; i++) {
            System.out.println("NonStaticSyncTest");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        SynchronizedTest synchronizedTest = new SynchronizedTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedTest.StaticSyncTest();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedTest.NonStaticSyncTest();
            }
        }).start();
    }
}
