package com.xunmall.example.java.concurrent.lock;

import org.junit.Test;


/**
 * @Author: WangYanjing
 * @Date: 2019/1/8 9:42
 * @Description:
 */
public class VolatileTest {
    volatile boolean exitFlag;

    private void tryExit() {
        if (exitFlag == !exitFlag) {
            System.exit(0);
        }
    }

    private void swapValue() {
        exitFlag = !exitFlag;
    }

    @Test
    public void testVolatileValue() throws Exception {
        final VolatileTest volatileTest = new VolatileTest();
        Thread mainThread = new Thread() {
            @Override
            public void run() {
                System.out.println("main Thread start!");
                while (true) {
                    volatileTest.tryExit();
                }
            }
        };
        mainThread.start();
        Thread slaveThread = new Thread() {
            @Override
            public void run() {
                System.out.println("slave Thread start!");
                while(true){
                    volatileTest.swapValue();
                }
            }
        };
        slaveThread.start();
        Thread.sleep(10000);
    }


}
