package com.rpc.mini.transport.server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 服务端请求处理类
 * @author xhzy
 */
public interface RequestHandler {

    /**
     * 接收客户端请求
     * @param inputStream 客户端输入
     * @param outputStream 客户端响应
     */
    void receive(InputStream inputStream, OutputStream outputStream);
}
