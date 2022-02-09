package com.xunmall.example.java.concurrent.threadpoolexctor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyj03@zenmen.com
 * @description
 * @date 2020/10/14 18:44
 */
public class ScheduleManagerDemo {

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4 * Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "DataCollectorManager-ScheduleTask");
            }
        });
        scheduler.scheduleAtFixedRate(new MyTask(1), 1, 15, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new MyTask(2), 2, 15, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new MyTask(3), 3, 15, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new MyTask(4), 4, 15, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new MyTask(5), 5, 15, TimeUnit.SECONDS);
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    static class MyTask implements Runnable {

        private int number;

        public MyTask(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println("正在执行：" + number);
        }
    }


}
