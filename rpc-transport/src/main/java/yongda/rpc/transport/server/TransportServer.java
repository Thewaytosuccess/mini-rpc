package yongda.rpc.transport.server;

import yongda.rpc.transport.handler.RequestHandler;

/**
 * 网络服务端
 * 1.启动，监听特定端口
 * 2.响应请求
 * 3.关闭
 * @author cdl
 */
public interface TransportServer {

    /**
     * 服务初始化
     * @param port 绑定的端口
     * @param handler 回调接口
     */
    void init(int port, RequestHandler handler);

    /**
     * 服务启动
     */
    void start();

    /**
     * 服务关闭
     */
    void stop();
}
