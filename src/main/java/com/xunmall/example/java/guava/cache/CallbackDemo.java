package com.xunmall.example.java.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class CallbackDemo {
    public static void main(String[] args) {
        Cache<String,String> cache= CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build();
        try {
            String result=cache.get("java", () -> "hello java");
            System.out.println(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
