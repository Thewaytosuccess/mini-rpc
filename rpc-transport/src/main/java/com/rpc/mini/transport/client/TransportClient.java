package com.rpc.mini.transport.client;

import com.rpc.mini.Peer;

import java.io.InputStream;

/**
 * 网络传输客户端
 * 1.与服务端建立连接
 * 2.向服务端发送数据，获取响应
 * 3.关闭与服务端连接
 * @author xhzy
 */
public interface TransportClient {

    /**
     * 与服务端建立连接
     * @param peer 网络端点
     */
    void connect(Peer peer);

    /**
     * 发送数据
     * @param inputStream 数据流
     * @return 响应数据
     */
    InputStream write(InputStream inputStream);

    /**
     * 关闭连接
     */
    void close();

}
