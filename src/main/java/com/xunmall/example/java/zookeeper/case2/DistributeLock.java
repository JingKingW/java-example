package com.xunmall.example.java.zookeeper.case2;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/1/28 15:28
 */
public class DistributeLock {

    private ZooKeeper zookeepwer;
    private final String connectionStr = "10.100.31.13:2181";
    private final int sessionTimeout = 30000;
    private CountDownLatch initLatch = new CountDownLatch(1);

    private CountDownLatch syncLatch = new CountDownLatch(1);

    private String currentNode;

    private String waitNode;

    public DistributeLock() throws IOException, InterruptedException, KeeperException {
        zookeepwer = new ZooKeeper(connectionStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    initLatch.countDown();
                }
                if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitNode)) {
                    syncLatch.countDown();
                }
            }
        });
        initLatch.await();

        Stat exists = zookeepwer.exists("/locks", false);

        if (exists == null) {
            zookeepwer.create("/locks", "locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public void zkLock() {
        try {
            currentNode = zookeepwer.create("/locks/" + "seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> children = zookeepwer.getChildren("/locks", false);
            if (children.size() == 1) {
                return;
            } else {
                Collections.sort(children);
                String thisNode = currentNode.substring("/locks/".length());
                int currentIndex = children.indexOf(thisNode);
                if (currentIndex == -1) {
                    System.out.println("数据异常");
                } else if (currentIndex == 0) {
                    return;
                } else {
                    waitNode = "/locks/" + children.get(currentIndex - 1);
                    zookeepwer.getData(waitNode, true, new Stat());
                    syncLatch.await();
                    return;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void zkUnLock() {
        try {
            zookeepwer.delete(currentNode, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
