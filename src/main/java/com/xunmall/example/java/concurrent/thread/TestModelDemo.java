package com.xunmall.example.java.concurrent.thread;

/**
 * Created by Administrator on 2017/3/25 0025.
 */
class Message{
    private String title;
    private String context;
    private boolean flag = true;

    public synchronized void set(String title,String context){
        if(this.flag == false){
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.title = title;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.context = context;
        this.flag =false;
        super.notify();
    }

    public synchronized void get(){
        if(this.flag == true){
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.title + " >>>>"+ this.context);
        this.flag = true;
        super.notify();
    }

}

class Productor implements Runnable{
    private Message message;

    public Productor(Message message){
        this.message = message;
    }

    @Override
    public void run() {
        for(int i = 0; i < 50; i++){
            if(i % 2 == 0){
               this.message.set("小明","爱读书！");
            }else{
               this.message.set("可爱的小动物","草泥马！");
            }
        }
    }
}

class Customer implements Runnable{
    private Message message;

    public Customer(Message message){
        this.message = message;
    }
    @Override
    public void run() {
        for(int i = 0; i < 50; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           this.message.get();
        }
    }
}
public class TestModelDemo {
    public static void main(String[] args){
        Message message = new Message();
        Productor p =new Productor(message);
        Customer c = new Customer(message);
        new Thread(p).start();
        new Thread(c).start();
    }

}
