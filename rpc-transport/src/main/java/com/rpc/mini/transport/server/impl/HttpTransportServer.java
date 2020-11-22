package com.rpc.mini.transport.server.impl;

import com.rpc.mini.transport.server.RequestHandler;
import com.rpc.mini.transport.server.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author xhzy
 */
@Slf4j
public class HttpTransportServer implements TransportServer {

    private RequestHandler handler;

    private Server server;

    @Override
    public void init(int port, RequestHandler requestHandler) {
        this.handler = requestHandler;
        //创建jettyServer
        server = new Server(port);
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet()),"/*");
        server.setHandler(contextHandler);
    }

    @Override
    public void start() {
        if(Objects.nonNull(server)){
            try {
                server.start();
                //避免server响应后立即关闭，让线程处于等待状态
                server.join();
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    @Override
    public void stop() {
        try {
            if(Objects.nonNull(server)){
                server.stop();
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    class DispatcherServlet extends HttpServlet{

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            log.info("[client connected]=====");
            if(Objects.nonNull(handler)){
                handler.receive(req.getInputStream(),resp.getOutputStream());
            }
        }
    }
}
