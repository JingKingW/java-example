package com.xunmall.example.java.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author wangyanjing
 * @date 2020/6/9
 */
public class ReenterLockCondition implements Runnable {

    private static ReentrantLock reentrantLock = new ReentrantLock();

    private static Condition condition = reentrantLock.newCondition();

    @Override
    public void run() {
        reentrantLock.lock();
        try{
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition reenterLockCondition = new ReenterLockCondition();
        Thread thread = new Thread(reenterLockCondition);
        thread.start();
        System.out.println("main is executor");
        Thread.sleep(2000);
        reentrantLock.lock();
        try {
            condition.signal();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }
}
