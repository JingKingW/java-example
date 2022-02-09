package com.xunmall.example.java.concurrent.threadpoolexctor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yanjing_wang on 2017/4/18.
 */
public class ThreadPoolExectorDemo {

    public static void main(String[] args) {
        int corePoolSize = Runtime.getRuntime().availableProcessors() << 1;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, 200, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(5), namedThreadFactory);
        try {
            for (int i = 1; i < 15; i++) {
                MyTask task = new MyTask(i);
                executor.execute(task);
                System.out.println("现在线程数量是：" + executor.getCorePoolSize() + "  等待队列中的任务数量是：" + executor.getQueue().size() + "  已经完成的任务数量：》》" + executor.getCompletedTaskCount());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    static class MyTask implements Runnable {

        private int number;

        public MyTask(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println("正在执行：" + number);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("已经执行完成：" + number);
        }
    }


}
