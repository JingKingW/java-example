package com.xunmall.example.java.guava.eventBus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class DeadEventListener {
    private boolean isDelivered = true;

    @Subscribe
    public void listen(DeadEvent event) {
        isDelivered = false;
        // source通常是EventBus
        System.out.println(event.getSource().getClass() + "  " + event.getEvent());
    }

    public boolean isDelivered() {
        return isDelivered;
    }
}
