package com.xunmall.example.java.guava;

import com.google.common.util.concurrent.RateLimiter;

/**
 *
 * @author wangyanjing
 * @date 2020/2/1
 */
public class RateLimiterDemo {
    static RateLimiter rateLimiter = RateLimiter.create(2);

    public static class Task implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i =0; i < 50; i++){
            rateLimiter.acquire();
            new Thread(new Task()).start();
        }
    }

}
