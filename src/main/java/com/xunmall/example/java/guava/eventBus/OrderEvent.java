package com.xunmall.example.java.guava.eventBus;

/**
 * Guava 发布-订阅模式中传递的事件,是一个普通的POJO类
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class OrderEvent {
    private String message;

    public OrderEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
