package com.xunmall.example.java.concurrent.juc;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Gimgoog
 * @date 2018/4/11
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            countDownLatch.countDown();
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            countDownLatch.countDown();
        });
        thread1.start();
        thread2.start();
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName());
    }
}
