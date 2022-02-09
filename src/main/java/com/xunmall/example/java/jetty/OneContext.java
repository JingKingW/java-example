package com.xunmall.example.java.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

/**
 * @author wangyj03
 */
public class OneContext {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        //在/hello路径上增加一个处理器
        ContextHandler context = new ContextHandler();
        context.setContextPath("/hello");
        context.setHandler(new HelloHandler());

        //可以通过http://localhost:8080/hello访问
        server.setHandler(context);

        server.start();
        server.join();
    }
}
