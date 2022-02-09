package com.xunmall.example.java.concurrent.lock;

/**
 * Created by Gimgoog on 2018/3/9.
 */
public class VolatileDemo extends Thread {
    private volatile boolean flag = false;

    private void stopMe(){
        flag = true;
    }

    @Override
    public void run(){
        int i = 0;
        while (!flag){
            i++;
        }
        System.out.println("停止操作！");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.start();
        VolatileDemo.sleep(1000);
        volatileDemo.stopMe();
        VolatileDemo.sleep(1000);
    }

}
