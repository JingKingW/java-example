package com.xunmall.example.java.guava.bloom;

import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: WangYanjing
 * @date: 2020年06月09日 14:08
 */
@Slf4j
public class RedisBloomFilterTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-redis-bloom.xml");
        RedisBloomFilter redisBloomFilter = (RedisBloomFilter) applicationContext.getBean("redisBloomFilter");
        int expectedInsertions = 1000;
        double fpp = 0.1;
        redisBloomFilter.delete("bloom");
        BloomFilterHelper<CharSequence> bloomFilterHelper = new BloomFilterHelper<>(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
        int j = 0;
        // 添加100个元素
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            valueList.add(i + "");
        }
        long beginTime = System.currentTimeMillis();
        redisBloomFilter.addList(bloomFilterHelper, "bloom", valueList);
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("布隆过滤器添加{}个值，耗时：{}ms", 100, costMs);
        System.out.println("布隆过滤器添加{}个值，耗时：{}ms" +  100 + costMs);
        for (int i = 0; i < 1000; i++) {
            boolean result = redisBloomFilter.contains(bloomFilterHelper, "bloom", i + "");
            if (!result) {
                j++;
            }
        }
        log.info("漏掉了{}个,验证结果耗时：{}ms", j, System.currentTimeMillis() - beginTime);
        System.out.println("漏掉了{}个,验证结果耗时：{}ms" +  (System.currentTimeMillis() - beginTime));

    }

}
