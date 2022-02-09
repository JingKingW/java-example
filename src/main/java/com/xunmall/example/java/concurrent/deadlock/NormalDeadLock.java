package com.xunmall.example.java.concurrent.deadlock;

/**
 * @Author: WangYanjing
 * @Date: ${Date} ${Time}
 * @Description:
 */
public class NormalDeadLock {
    private static Object first = new Object();
    private static Object second = new Object();

    private static void firstSecond() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (first) {
            System.out.println(threadName + " get first");
            Thread.sleep(100);
            synchronized (second) {
                System.out.println(threadName + " get second");
            }
        }
    }

    private static void secondFirst() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (second) {
            System.out.println(threadName + " get second");
            Thread.sleep(100);
            synchronized (first) {
                System.out.println(threadName + " get first");
            }
        }
    }

    private static class SubThread extends Thread {

        private String name;

        public SubThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                secondFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("MainThread");
        SubThread subThread = new SubThread("SubThread");
        subThread.start();
        firstSecond();
    }

}
