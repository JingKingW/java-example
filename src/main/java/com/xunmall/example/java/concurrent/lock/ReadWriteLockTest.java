package com.xunmall.example.java.concurrent.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Gimgoog on 2018/6/5.
 */
public class ReadWriteLockTest {

    private static Lock reentryLock = new ReentrantLock();
    private static ReadWriteLock readLock = new ReentrantReadWriteLock();
    private static Lock read = readLock.readLock();
    private static Lock write = readLock.writeLock();
    private String value;

    public String readHandReentrant() throws InterruptedException {
        reentryLock.lock();
        try {
            return value;
        } finally {
            reentryLock.unlock();
        }
    }

    public void writeHandReentrant(String string) throws InterruptedException {
        reentryLock.lock();
        try {
            value = string;
        } finally {
            reentryLock.unlock();
        }
    }

    public String readHandLock() throws InterruptedException {
        read.lock();
        try {
            return value;
        } finally {
            read.unlock();
        }
    }

    public void writeHandLock(String string) throws InterruptedException {
        write.lock();
        try {
            value = string;
        } finally {
            write.unlock();
        }
    }

    @Test
    public void testReadAndWriteLock() throws InterruptedException {
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Thread writeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readWriteLockTest.writeHandLock(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            writeThread.start();
            writeThread.join();
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readWriteLockTest.readHandLock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThread.start();
            readThread.join();
        }

        Long endTime = System.currentTimeMillis();
        System.out.println("总共耗时：" + (endTime - startTime));
    }

    @Test
    public void testReentrantLock() throws InterruptedException {
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Thread writeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readWriteLockTest.writeHandReentrant(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            writeThread.start();
            writeThread.join();
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readWriteLockTest.readHandReentrant();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThread.start();
            readThread.join();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("总共耗时：" + (endTime - startTime));
    }


}
