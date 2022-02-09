package com.xunmall.example.java.zookeeper;

import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/1/19 11:40
 */
public class ZkSampleTest {

    private static String connnectiongUrl = "10.100.31.13:2181";

    private static int sessionTimeout = 30000;

    private ZooKeeper zooKeeper;

    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(connnectiongUrl, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                List<String> children = null;
                System.out.println("===============================");
                try {
                    children = zooKeeper.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String child : children) {
                    System.out.println(child);
                }
            }
        });
    }

    @After
    public void destroy() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }


    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        String result = zooKeeper.create("/sanguo", "zuiailiaocai".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("===================" + result + "=======================");
    }

    @Test
    public void testGetChild() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/", false);
        for (String child : children) {
            System.out.println(child);
        }

        Thread.sleep(Long.MAX_VALUE);
    }




}
