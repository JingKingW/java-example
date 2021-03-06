package com.xunmall.example.java.concurrent.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangyanjing on 2020/2/3.
 */
public class ThreadLocalDemo_Gc {

    static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc");
        }
    };

    static volatile CountDownLatch cd = new CountDownLatch(10000);

    public static class ParseDate implements Runnable {

        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if (t1.get() == null) {
                t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                    @Override
                    protected void finalize() throws Throwable {
                        System.out.println(this.toString() + " is GC");
                    }
                });

                System.out.println(Thread.currentThread().getId() + "create SimpleDateFormat");
            }

            try {
                Date t = t1.get().parse("2020-02-03 15:20:" + i % 60);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                cd.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++){
            executorService.execute(new ParseDate(i));
        }
        cd.await();
        System.out.println("mission complete!!");
        t1 = null;
        System.gc();
        System.out.println("first GC complete!!");
        t1 = new ThreadLocal<SimpleDateFormat>();
        cd = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++){
            executorService.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second GC complete!!");

    }


}
