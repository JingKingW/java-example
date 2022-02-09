package com.xunmall.example.java.guava;

import com.google.common.util.concurrent.*;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author wangyanjing
 * @date 2020/7/28 10:06
 */
public class GuavaFutureDemo {

    public static final int SLEEP_GAP = 500;

    /**
     * @Description: [获取当前线程名称]
     * @Title: getCurrentThreadName
     * @Author: WangYanjing
     * @Date: 2020/8/21
     * @Param:
     * @Return: java.lang.String
     * @Throws:
     */
    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWaterJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            Thread.sleep(new Random().nextInt(1000));
            System.out.println("烧水完成");
            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            Thread.sleep(new Random().nextInt(2000));
            System.out.println("清洗完成");
            return true;
        }
    }

    static class MainJob implements Runnable {
        volatile boolean waterOk = false;

        volatile boolean washOk = false;

        int gap = SLEEP_GAP / 10;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(gap);
                    System.out.println("读书中。。。。");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                drinkTea(waterOk, washOk);
            }
        }

        public void drinkTea(boolean waterOk1, boolean washOk1) {
            if (waterOk1 && washOk1) {
                System.out.println("泡茶喝，茶喝完");

                this.washOk = false;
                this.washOk = false;

                this.gap = SLEEP_GAP * 100;

            } else if (!waterOk1) {
                System.out.println("水没烧开呢");
            } else if (!washOk1) {
                System.out.println("杯子没洗好");
            }

        }
    }

    public static void main(String[] args) {
        MainJob mainJob = new MainJob();

        Thread mainThread = new Thread(mainJob);

        mainThread.setName("主线程");
        mainThread.start();

        Callable<Boolean> waterJob = new HotWaterJob();

        Callable<Boolean> washJob = new WashJob();

        // guava将jdk自带的线程池进行二次封装，支持异步响应
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

        ListenableFuture hotWaterFuture = listeningExecutorService.submit(waterJob);
        Futures.addCallback(hotWaterFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    mainJob.waterOk = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("烧水失败");
            }
        });

        ListenableFuture<Boolean> washFuture = listeningExecutorService.submit(washJob);
        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    mainJob.washOk = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("洗杯子失败");
            }
        });


    }


}
