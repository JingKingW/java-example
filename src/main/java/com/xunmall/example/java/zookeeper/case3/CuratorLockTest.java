package com.xunmall.example.java.zookeeper.case3;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/1/29 16:32
 */
public class CuratorLockTest {

    public static void main(String[] args) {

        InterProcessMutex lock1 = new InterProcessMutex(getCuratorFramework(), "/locks");

        InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(), "/locks");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.acquire();
                    System.out.println("线程1 获取到锁");
                    lock1.acquire();
                    System.out.println("线程1 再次获取到锁");
                    Thread.sleep(5 * 1000);
                    lock1.release();
                    System.out.println("线程1 释放锁");
                    lock1.release();
                    System.out.println("线程1 再次释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.acquire();
                    System.out.println("线程2 获取到锁");
                    lock2.acquire();
                    System.out.println("线程2 再次获取到锁");
                    Thread.sleep(5 * 1000);
                    lock2.release();
                    System.out.println("线程2 释放锁");
                    lock2.release();
                    System.out.println("线程2 再次释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    private static CuratorFramework getCuratorFramework() {

        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(3000, 3);

        CuratorFramework build = CuratorFrameworkFactory.builder().connectString("10.100.31.13:2181").connectionTimeoutMs(30000).sessionTimeoutMs(30000).retryPolicy(retry).build();

        build.start();

        System.out.println("zookeeper 启动成功");

        return build;
    }

}
