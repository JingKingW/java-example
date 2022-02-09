package com.xunmall.example.java.zookeeper.case2;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/1/29 15:21
 */
public class DistributeLockTest {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {

        final DistributeLock distributeLock1 = new DistributeLock();

        final DistributeLock distributeLock2 = new DistributeLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                distributeLock1.zkLock();
                System.out.println("线程1获取到锁");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                distributeLock1.zkUnLock();
                System.out.println("线程1释放锁");
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                distributeLock2.zkLock();
                System.out.println("线程2获取到锁");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                distributeLock2.zkUnLock();
                System.out.println("线程2释放锁");
            }
        }).start();

    }

}
