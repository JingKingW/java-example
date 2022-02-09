package com.xunmall.example.java.guava.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author owen5
 * @date 2017/12/2
 */
public class BloomFilterTest {


    @Test
    public void testBloomFilter() throws Exception {
        // 插入多少数据
        int insettions = 1000000;
        //期望的误判率
        double fpp = 0.02;

        // 创建布隆过滤器  第二个参数为预期数据量10000000，第三个参数为错误率0.02
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), insettions, fpp);

        // 用于存放所有实际存在的key，用于是否存在
        Set<String> set = new HashSet<>(insettions);
        // 用于存放所有实际存在的key，用于取出
        List<String> list = new ArrayList<String>(insettions);

        // 插入随机字符串
        for (int i = 0; i < insettions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            set.add(uuid);
            list.add(uuid);
        }

        int rightNum = 0;
        int wrongNum = 0;

        for (int i = 0; i < 10000; i++) {
            // 0-10000之间，可以被100整除的数有100个（100的倍数）
            String item = i % 100 == 0 ? list.get(i / 100) : UUID.randomUUID().toString();

            // 这里用了might,看上去不是很自信，所以如果布隆过滤器判断存在了,我们还要去sets中实锤
            if (bf.mightContain(item)) {
                if (set.contains(item)) {
                    rightNum++;
                    continue;
                }
                wrongNum++;
            }
        }

        BigDecimal percent = new BigDecimal(wrongNum).divide(new BigDecimal(9900), 2, RoundingMode.HALF_UP);
        BigDecimal bingo = new BigDecimal(9900 - wrongNum).divide(new BigDecimal(9900), 2, RoundingMode.HALF_UP);
        System.out.println("在100W个元素中，判断100个实际存在的元素，布隆过滤器认为存在的：" + rightNum);
        System.out.println("在100W个元素中，判断9900个实际不存在的元素，误认为存在的：" + wrongNum + "，命中率：" + bingo + "，误判率：" + percent);

    }

    @Test
    public void testMyBloomFilter() {
        int insettions = 100000;
        MyBloomFilter bf = new MyBloomFilter();
        Set<String> set = new HashSet<>(insettions);
        List<String> list = new ArrayList<String>(insettions);

        for (int i = 0; i < insettions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.add(uuid);
            set.add(uuid);
            list.add(uuid);
        }
        int right = 0;
        int wrong = 0;
        for (int i = 0; i < 100; i++) {
            String item = i % 100 == 0 ? list.get(i) : UUID.randomUUID().toString();
            if (bf.contains(item)) {
                if (set.contains(item)) {
                    right++;
                } else {
                    wrong++;
                }
            }
        }

        System.out.println("正确命中的:" + right);
        System.out.println("错误命中的:" + wrong);
    }

}
