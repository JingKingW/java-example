package com.xunmall.example.java.concurrent.thread.db;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: WangYanjing
 * @Date: 2019/1/4 11:04
 * @Description:
 */
public class ConnectionPoolTest {

    static ConnectionPool connectionPool = new ConnectionPool(10);
    //保证所有的ConnectionRunner能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    //主线程将会等待所有ConnectionRunner执行结束后才继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        //线程数据数量，可以通过修改线程数量来对比观察
        int threadCount = 30;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();

        System.out.println("total invoke " + (threadCount * count));
        System.out.println("got connection " + got);
        System.out.println("not got connection " + notGot);

    }

    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            while (count > 0) {
                try {
                    Connection connection = connectionPool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            connectionPool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

}
