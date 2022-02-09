package com.xunmall.example.java.concurrent.threadlocal;

/**
 * Created by wangyanjing on 2018/11/7.
 */
public class SequenceNumber {

    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
        @Override
        public Integer initialValue() {
            return 0;
        }
    };

    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {
        SequenceNumber sequenceNumber = new SequenceNumber();
        Thread thread1 = new ThreadLocalClient(sequenceNumber);
        Thread thread2 = new ThreadLocalClient(sequenceNumber);
        Thread thread3 = new ThreadLocalClient(sequenceNumber);
        thread1.setName("thread A");
        thread2.setName("thread B");
        thread3.setName("thread C");
        thread1.start();
        thread2.start();
        thread3.start();
    }

    static class ThreadLocalClient extends Thread {

        private SequenceNumber sequenceNumber;

        public ThreadLocalClient(SequenceNumber sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + sequenceNumber.getNextNum());
            }
        }
    }

}
