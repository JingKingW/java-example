package com.xunmall.example.java.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * @author wangyj03
 */
public class MinimalServlets {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
        //ServletHandler通过一个servlet创建了一个非常简单的context处理器
        //这个处理器需要在Server上注册
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        //传入能匹配到这个servlet的路径
        //提示：这是一个未经处理的servlet，没有通过web.xml或@WebServlet注解或其他方式配置
        handler.addServletWithMapping(HelloServlet.class, "/*");

        server.start();
        server.join();
    }

    @SuppressWarnings("serial")
    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Hello from HelloServlet</h1>");
        }
    }
}
