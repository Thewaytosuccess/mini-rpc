package yongda.rpc.transport.client;

import yongda.rpc.proto.Peer;
import yongda.rpc.proto.Request;
import yongda.rpc.proto.Response;

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
     * http client方式，向服务端发送请求
     * @param is 输入流，请求参数
     * @return 输出流，返回结果序列化的二进制流
     */
    default InputStream sendRequest(InputStream is){
        return null;
    }

    /**
     *  netty client方式发送请求
     * @param request 入参
     */
    default Response sendRequest(Request request){ return null;}

    /**
     * 关闭与服务端建立的连接
     */
    void close();
}
