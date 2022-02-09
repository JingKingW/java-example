package com.xunmall.example.java.zookeeper.case1;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/1/28 14:43
 */
public class DistributeClient {

    private String connectionStr = "10.100.31.13:2181";
    private int sessionTimeout = 30000;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributeClient client = new DistributeClient();

        client.getConnection();

        client.registerListenerServer();

        client.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void registerListenerServer() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/servers", true);
        if (children.size() > 0){
            System.out.println(children);
        }
    }

    private void getConnection() throws IOException {
        zooKeeper = new ZooKeeper(connectionStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    registerListenerServer();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
