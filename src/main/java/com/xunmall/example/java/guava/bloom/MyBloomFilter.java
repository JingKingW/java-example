package com.xunmall.example.java.guava.bloom;

import java.util.BitSet;

/**
 * @Description: TODO
 * @author: WangYanjing
 * @date: 2020年05月26日 11:06
 */
public class MyBloomFilter {
    /**
     * 功能描述:  布隆过滤器的长度
     *
     * @params:
     * @return:
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:18
     **/
    private static final int SIZE = 2 << 10;
    /**
     * 功能描述:  模拟实现不同的哈希函数
     *
     * @params:
     * @return:
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:18
     **/
    private static final int[] num = new int[]{5, 19, 23, 31, 47, 71};
    /**
     * 功能描述:  初始化位数组
     *
     * @params:
     * @return:
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:18
     **/
    private BitSet bits = new BitSet(SIZE);
    /**
     * 功能描述:   用于存储哈希函数
     *
     * @params:
     * @return:
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:18
     **/
    private MyHash[] function = new MyHash[num.length];

    /**
     * 功能描述: 初始化hash函数
     *
     * @params:
     * @return:
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:19
     **/
    public MyBloomFilter() {
        for (int i = 0; i < num.length; i++) {
            function[i] = new MyHash(SIZE, num[i]);
        }
    }

    /**
     * 功能描述: 存值api
     *
     * @params: [value]
     * @return: void
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:40
     **/
    public void add(String value) {
        // 对存入的值进行hash计算
        for (MyHash f : function) {
            // 将为数组对应的哈希下标的位置值改为1
            bits.set(f.hash(value), true);
        }
    }

    /**
     * 功能描述: 判断是否存在该值对的api
     *
     * @params:
     * @return:
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:43
     **/
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean result = true;
        for (MyHash f : function) {
            result = result && bits.get(f.hash(value));
        }
        return result;
    }

}
