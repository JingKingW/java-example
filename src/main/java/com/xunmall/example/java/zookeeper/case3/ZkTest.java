package com.xunmall.example.java.zookeeper.case3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/2/8 11:41
 */
public class ZkTest extends Thread {

    static int inventory = 1;
    private static final int Num = 10;
    private static CountDownLatch cdl = new CountDownLatch(Num);
    private static Lock lock = new ReentrantLock(false);

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        for (int i = 1; i <= Num; i++) {
            new Thread(new ZkTest()).start();
            cdl.countDown();
        }
        System.out.println("total:" + (System.nanoTime() - startTime));
    }

    @Override
    public void run() {
        lock.lock();
        try {
            cdl.await();
            if (inventory > 0) {
                Thread.sleep(5);
                inventory--;
            }
            System.out.println(inventory);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
