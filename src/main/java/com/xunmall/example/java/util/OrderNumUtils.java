package com.xunmall.example.java.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: WangYanjing
 * @Date: 2018/12/27 10:53
 * @Description: <p>订单流水号生成器，最大并发数量为10万</p>
 */
public class OrderNumUtils {
    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "Rocky.J";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 1L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int maxPerMSECSize = 1000;

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     *
     * @param startName 测试用
     */
    public static String makeOrderNum(String startName) {
        try {
            // 最终生成的订单号
            String finOrderNum = "";
            synchronized (lockObj) {
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值100000个，1秒10000万
                if (orderNumCount > maxPerMSECSize) {
                    orderNumCount = 1L;
                }
                // 组装订单号
                String countStr = maxPerMSECSize + orderNumCount + "";
                finOrderNum = startName.toUpperCase() + nowLong + countStr.substring(1);
                orderNumCount++;
            }
            return finOrderNum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 16位订单号
     *
     * @param machineId
     * @return
     */
    public static String genId(String machineId) {
        String orderId =
                machineId +
                        (System.currentTimeMillis() + "").substring(1) +
                        (System.nanoTime() + "").substring(7, 10);
        return orderId;
    }

    /**
     * 1、在100个线程并发下，生成20万条雪花ID的时间大概在1.6秒左右，所有所性能还是蛮ok的。
     * 2、生成20万条雪花ID并没有一条相同的ID,因为有一条就会抛出异常了。
     */
    public static void main(String[] args) throws InterruptedException {
        //计时开始时间
        long start = System.currentTimeMillis();
        //让100个线程同时进行
        final CountDownLatch latch = new CountDownLatch(100);
        //判断生成的20万条记录是否有重复记录
        final Map<String, Integer> map = new ConcurrentHashMap();
        for (int i = 0; i < 100; i++) {
            //创建100个线程
            new Thread(() -> {
                for (int s = 0; s < 2000; s++) {
                    String snowID = OrderNumUtils.makeOrderNum("");
                    System.out.println("生成时间戳ID={}" + snowID);
                    Integer put = map.put(snowID, 1);
                    if (put != null) {
                        throw new RuntimeException("主键重复");
                    }
                }
                latch.countDown();
            }).start();
        }
        //让上面100个线程执行结束后，在走下面输出信息
        latch.await();
        System.out.println("生成20万条时间戳ID总用时={}" + (System.currentTimeMillis() - start));
    }
}