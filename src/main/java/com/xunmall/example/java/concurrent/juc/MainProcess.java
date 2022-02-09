package com.xunmall.example.java.concurrent.juc;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author wangyanjing
 */
public class MainProcess {

    public static void main(String[] args) {

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        for (ThreadInfo threadInfo : threadInfos) {

            System.out.println(threadInfo.getThreadId() + " ： " + threadInfo.getThreadName());

        }

    }


}
