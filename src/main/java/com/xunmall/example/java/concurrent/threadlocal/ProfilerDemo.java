package com.xunmall.example.java.concurrent.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @Author: WangYanjing
 * @Date: 2019/1/3 16:10
 * @Description:
 */
public class ProfilerDemo {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ProfilerDemo.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + ProfilerDemo.end() + " Mills");

    }


}
