package yongda.rpc.client.selector;

import yongda.rpc.proto.Peer;
import yongda.rpc.transport.client.TransportClient;

import java.util.List;

/**
 * 选择一个服务端建立连接
 * @author cdl
 */
public interface TransportSelector {

    /**
     * 建立客户端和所有服务端的连接
     * @param peers 所有启动的server list
     * @param count 建立的连接数
     * @param transportClient 选择的客户端
     */
    void init(List<Peer> peers,int count,Class<? extends TransportClient> transportClient);

    /**
     * 选择一个服务端建立连接
     * @return 建立连接的客户端
     */
    TransportClient select();

    /**
     * 释放已经连接的客户端
     * @param client 客户端
     */
    void release(TransportClient client);

    /**
     * 关闭所有连接
     */
    void close();
}
