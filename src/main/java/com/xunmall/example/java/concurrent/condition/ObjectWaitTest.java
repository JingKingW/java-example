package com.xunmall.example.java.concurrent.condition;

/**
 * Created by wangyanjing on 2018/12/19.
 */
public class ObjectWaitTest {

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA("ta");
        synchronized (threadA){
            try{
                System.out.println(Thread.currentThread().getName() + " start ta");
                threadA.start();

                System.out.println(Thread.currentThread().getName() + " block");
                threadA.wait();

                System.out.println(Thread.currentThread().getName() + " continue");
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    static class ThreadA extends Thread{

        public ThreadA(String name){
            super(name);
        }

        @Override
        public void run() {
            synchronized (this){
                System.out.println(Thread.currentThread().getName() + "wakeup others");
                notify();
            }
        }
    }











}
