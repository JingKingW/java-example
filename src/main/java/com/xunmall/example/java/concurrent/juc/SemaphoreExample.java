package com.xunmall.example.java.concurrent.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: WangYanjing
 * @Date: 2019/2/14 9:27
 * @Description:
 */
public class SemaphoreExample {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int index = 0; index < 20; index++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        Thread.sleep((long) Math.random());
                        semaphore.release();
                        System.out.println("剩余的信号量" + semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }


}
