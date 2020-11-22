package com.rpc.mini.client.selector;

import com.rpc.mini.Peer;
import com.rpc.mini.transport.client.TransportClient;

import java.util.List;

/**
 * @author xhzy
 */
public interface TransportClientSelector {

    /**
     * 初始化客户端
     * @param servers 服务端点列表
     * @param count 需要建立的连接个数
     * @param clazz transportClient
     */
    void init(List<Peer> servers, int count, Class<? extends TransportClient> clazz);

    /**
     * 选择一个服务端点，与之建立连接
     * @return 建立连接的客户端
     */
    TransportClient select();

    /**
     * 释放一个网络连接
     * @param client 网络连接
     */
    void release(TransportClient client);

    /**
     * 关闭所有连接
     */
    void close();


}
