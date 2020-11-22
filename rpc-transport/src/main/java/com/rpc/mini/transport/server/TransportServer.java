package com.rpc.mini.transport.server;

/**
 * 网络传输服务端
 * 1.启动，监听某个端口
 * 2.接收客户端连接
 * 3.关闭连接
 * @author xhzy
 */
public interface TransportServer {

    /**
     * 监听某个端口
     * @param port 监听端口
     * @param handler 流处理器
     */
    void init(int port,RequestHandler handler);

    /**
     * 启动
     */
    void start();

    /**
     * 关闭连接
     */
    void stop();
}
