package yongda.rpc.transport.client;

import yongda.rpc.proto.Peer;

import java.io.InputStream;

/**
 * @author cdl
 *
 * 网络客户端
 * 1.获取连接
 * 2.发送数据
 * 3.关闭
 */
public interface TransportClient {

    /**
     * 与服务端建立连接
     * @param peer 网络端点
     */
    void connect(Peer peer);

    /**
     * 向服务端发送请求
     * @param is 输入流，请求参数
     * @return 输出流，返回结果序列化的二进制流
     */
    InputStream sendRequest(InputStream is);

    /**
     * 关闭与服务端建立的连接
     */
    void close();
}
