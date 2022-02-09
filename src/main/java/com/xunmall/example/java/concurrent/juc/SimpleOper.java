package com.xunmall.example.java.concurrent.juc;

/**
 * @Author: WangYanjing
 * @Date: ${Date} ${Time}
 * @Description:
 */
public class SimpleOper {
    private int count = 0;

    public int getCount() {
        return this.count;
    }

    public synchronized void incCount() {
        count++;
    }

    private static class Count extends Thread {

        private SimpleOper simpleOper;

        public Count(SimpleOper simpleOper) {
            this.simpleOper = simpleOper;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                simpleOper.incCount();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleOper simpleOper = new SimpleOper();

        Count count1 = new Count(simpleOper);
        Count count2 = new Count(simpleOper);
        count1.start();
        count2.start();

        Thread.sleep(50);

        System.out.println(simpleOper.count);


    }


}
