package yongda.rpc.transport.server.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import yongda.rpc.transport.handler.RequestHandler;
import yongda.rpc.transport.server.TransportServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cdl
 */
@Slf4j
public class HttpTransportServer implements TransportServer {

    private RequestHandler requestHandler;

    private Server server;

    @Override
    public void init(int port, RequestHandler handler) {
        this.requestHandler = handler;

        //实例化jetty server
        server = new Server(port);
        //servlet注册到上下文中
        ServletContextHandler ctx = new ServletContextHandler();
        ctx.addServlet(new ServletHolder(new RpcServlet()),"/*");

        //上下文注册到server中
        server.setHandler(ctx);
    }

    @Override
    public void start() {
        try {
            //启动并等待连接
            server.start();

            //服务端启动后不立即关闭
            server.join();
        } catch (Exception e) {
            log.error("server 启动失败:{}",e);
        }

    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error("server 关闭失败:{}",e);
        }
    }

    class RpcServlet extends HttpServlet{
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            if(requestHandler != null){
                requestHandler.onRequest(req.getInputStream(),resp.getOutputStream());
            }
        }
    }
}
