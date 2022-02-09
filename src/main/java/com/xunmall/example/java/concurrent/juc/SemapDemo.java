package com.xunmall.example.java.concurrent.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author wangyanjing
 * @date 2020/1/31
 */
public class SemapDemo implements Runnable{

    Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + ":done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = newFixedThreadPool(20);
        SemapDemo semapDemo = new SemapDemo();
        for (int i =0; i<20;i++){
                executorService.execute(semapDemo);
        }
    }


}
