package com.xunmall.example.java.jetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WangYanjing
 * @description
 * @date 2020/9/16 11:51
 */
public class HelloWorld extends AbstractHandler {
    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        // 声明response的编码和文件类型
        httpServletResponse.setContentType("text/html; charset=utf-8");

        // 声明返回状态码
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        // 请求的返回值
        httpServletResponse.getWriter().println("<h1>Hello World</h1>");

        // 通知Jettyrequest使用此处理器处理请求
        request.setHandled(true);
    }

    public static void main(String[] args) throws Exception {
        //创建一个应用服务监听8080端口
        Server server = new Server(8080);
        server.setHandler(new HelloWorld());

        //启动应用服务并等待请求
        server.start();
        server.join();
    }
}
