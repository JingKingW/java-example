package com.xunmall.example.java.zookeeper.case1;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/1/28 14:43
 */
public class DistributeServer {

    private ZooKeeper zookeeper;

    private String connnectionStr = "10.100.31.13:2181";

    private int sessionTimeout = 30000;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        DistributeServer server = new DistributeServer();

        server.getConnection();

        server.registerServer(args[0]);

        server.business();
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void registerServer(String hostName) throws KeeperException, InterruptedException {
        zookeeper.create("/servers/" + hostName,hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName + " is online");
    }

    private void getConnection() throws IOException {
        zookeeper = new ZooKeeper(connnectionStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }


}
