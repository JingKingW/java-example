package com.xunmall.example.java.jetty;

import org.eclipse.jetty.server.Server;

/**
 * @author WangYanjing
 * @description
 * @date 2020/9/16 12:00
 */
public class SimplestServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}
