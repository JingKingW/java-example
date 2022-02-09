package com.xunmall.example.java.concurrent.threadpoolexctor;

/**
 * Created by owen5 on 2018/8/1.
 */
public class TwoThreadWaitNotify {

    private int start = 1;

    private boolean flag = false;

    public static void main(String[] args) {

        TwoThreadWaitNotify twoThreadWaitNotify = new TwoThreadWaitNotify();

        Thread ouThread = new Thread(new OuNumber(twoThreadWaitNotify));
        ouThread.setName("ou");
        Thread jiThread = new Thread(new JiNumber(twoThreadWaitNotify));
        jiThread.setName("ji");

        ouThread.start();
        jiThread.start();
    }

    public static class OuNumber implements Runnable {

        private TwoThreadWaitNotify number;

        public OuNumber(TwoThreadWaitNotify number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("偶数线程抢到锁了");
                    if (number.flag) {
                        System.out.println(Thread.currentThread().getName() + "++ 偶数值：" + number.start);
                        number.start++;

                        number.flag = false;

                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

    public static class JiNumber implements Runnable {

        private TwoThreadWaitNotify number;

        public JiNumber(TwoThreadWaitNotify number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("奇数线程抢到锁了");
                    if (!number.flag) {
                        System.out.println(Thread.currentThread().getName() + "++ 奇数值：" + number.start);
                        number.start++;

                        number.flag = true;

                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

}
