package com.xunmall.example.java.guava.eventBus;

import com.google.common.eventbus.Subscribe;

/**
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class MultiEventListener {

    @Subscribe
    public void listen(OrderEvent event){
        System.out.println("receive msg: "+event.getMessage());
    }

    @Subscribe
    public void listen(String message){
        System.out.println("receive msg: "+message);
    }
}
