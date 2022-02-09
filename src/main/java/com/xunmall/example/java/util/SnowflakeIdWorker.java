package com.xunmall.example.java.util;


import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
public class SnowflakeIdWorker {

    private static class SnowFlake {
        /**
         * 开始时间截 (2015-01-01)
         */
        private final long twepoch = 1489111610226L;

        /**
         * 机器id所占的位数
         */
        private final long workerIdBits = 5L;

        /**
         * 数据标识id所占的位数
         */
        private final long dataCenterIdBits = 5L;

        /**
         * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
         */
        private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

        /**
         * 支持的最大数据标识id，结果是31
         */
        private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

        /**
         * 序列在id中占的位数
         */
        private final long sequenceBits = 12L;

        /**
         * 机器ID向左移12位
         */
        private final long workerIdShift = sequenceBits;

        /**
         * 数据标识id向左移17位(12+5)
         */
        private final long dataCenterIdShift = sequenceBits + workerIdBits;

        /**
         * 时间截向左移22位(5+5+12)
         */
        private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

        /**
         * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
         */
        private final long sequenceMask = -1L ^ (-1L << sequenceBits);

        /**
         * 工作机器ID(0~31)
         */
        private long workerId;

        /**
         * 数据中心ID(0~31)
         */
        private long dataCenterId;

        /**
         * 毫秒内序列(0~4095)
         */
        private long sequence = 0L;

        /**
         * 上次生成ID的时间截
         */
        private long lastTimestamp = -1L;

        /**
         * 内部类对象（单例模式）
         */
        private static final SnowflakeIdWorker.SnowFlake SNOW_FLAKE = new SnowflakeIdWorker.SnowFlake();

        /**
         * 构造函数
         */
        private SnowFlake() {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
            }
            if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
                throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", maxDataCenterId));
            }
            this.workerId = getWorkId();
            this.dataCenterId = getDataCenterId();
        }

        /**
         * 获得下一个ID (该方法是线程安全的)
         *
         * @return SnowflakeId
         */
        public synchronized long nextId() {
            long timestamp = timeGen();

            //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(
                        String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }

            //如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            //时间戳改变，毫秒内序列重置
            else {
                sequence = 0L;
            }

            //上次生成ID的时间截
            lastTimestamp = timestamp;

            //移位并通过或运算拼到一起组成64位的ID
            return ((timestamp - twepoch) << timestampLeftShift)
                    | (dataCenterId << dataCenterIdShift)
                    | (workerId << workerIdShift)
                    | sequence;
        }

        /**
         * 阻塞到下一个毫秒，直到获得新的时间戳
         *
         * @param lastTimestamp 上次生成ID的时间截
         * @return 当前时间戳
         */
        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        /**
         * 返回以毫秒为单位的当前时间
         *
         * @return 当前时间(毫秒)
         */
        protected long timeGen() {
            return System.currentTimeMillis();
        }

        private static Long getWorkId() {
            try {
                String hostAddress = Inet4Address.getLocalHost().getHostAddress();
                int[] ints = StringUtils.toCodePoints(hostAddress);
                int sums = 0;
                for (int b : ints) {
                    sums += b;
                }
                return (long) (sums % 32);
            } catch (UnknownHostException e) {
                // 如果获取失败，则使用随机数备用
                return RandomUtils.nextLong(0, 31);
            }
        }

        private static Long getDataCenterId() {
            int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
            int sums = 0;
            for (int i : ints) {
                sums += i;
            }
            return (long) (sums % 32);
        }

    }

    /**
     * 获取long类型雪花ID
     */
    public static Long generateId() {
        long id = SnowflakeIdWorker.SnowFlake.SNOW_FLAKE.nextId();
        return id;
    }

    /**
     * 获取String类型雪花ID
     */
    public static String generateIdHex() {
        return String.format("%016x", generateId());
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
        final Map<Long, Integer> map = new ConcurrentHashMap();
        for (int i = 0; i < 100; i++) {
            //创建100个线程
            new Thread(() -> {
                for (int s = 0; s < 2000; s++) {
                    long snowID = SnowflakeIdWorker.generateId();
                    System.out.println("生成雪花ID： " + snowID);
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
        System.out.println("生成20万条雪花ID总用时={}" + (System.currentTimeMillis() - start));
    }

}
