package com.xunmall.example.java.jetty;

import org.eclipse.jetty.server.Server;

/**
 * @author WangYanjing
 * @description
 * @date 2020/9/16 14:15
 */
public class OneHandler {
    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new HelloHandler());

        server.start();
        server.join();
    }
}
