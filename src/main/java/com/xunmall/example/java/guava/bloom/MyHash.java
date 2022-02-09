package com.xunmall.example.java.guava.bloom;

/**
 * @Description: TODO
 * @author: WangYanjing
 * @date: 2020年05月26日 11:12
 */
public class MyHash {
    private int cap;
    private int seed;

    /**
     * 初始化数据
     */
    public MyHash(int cap, int seed) {
        this.cap = cap;
        this.seed = seed;
    }

    /**
     * 功能描述:  hash函数
     *
     * @params:
     * @return: int
     * @exception:
     * @Author: wangyanjing
     * @Date: 2020/5/26 11:16
     **/
    public int hash(String value) {
        int result = 0;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            result = seed * result + value.charAt(i);
        }
        return (cap - 1) & result;
    }

}
