package com.xunmall.example.java.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gimgoog
 * @date 2018/1/30
 */
public class LoadingCacheDemo {
    public static void main(String[] args) {
        // 最大缓存数目 缓存1秒后过期
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key;
                    }
                });
        cache.put("j", "java");
        cache.put("c", "cpp");
        cache.put("s", "scala");
        cache.put("g", "go");
        try {
            System.out.println(cache.get("j"));
            TimeUnit.SECONDS.sleep(2);
            System.out.println(cache.get("s"));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
