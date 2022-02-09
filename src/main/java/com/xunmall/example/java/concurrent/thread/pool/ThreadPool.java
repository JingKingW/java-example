package com.xunmall.example.java.concurrent.thread.pool;

/**
 * @Author: WangYanjing
 * @Date: 2019/1/4 11:54
 * @Description:
 */
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);

    void shutdown();

    void addWorkers(int number);

    void removeWorker(int number);

    int getJobSize();
}


