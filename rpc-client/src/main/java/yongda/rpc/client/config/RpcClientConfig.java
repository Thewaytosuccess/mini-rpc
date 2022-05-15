package yongda.rpc.client.config;

import lombok.Data;
import yongda.rpc.client.selector.impl.RandomTransportSelector;
import yongda.rpc.client.selector.TransportSelector;
import yongda.rpc.codec.decoder.Decoder;
import yongda.rpc.codec.encoder.Encoder;
import yongda.rpc.codec.decoder.impl.JSONDecoder;
import yongda.rpc.codec.encoder.impl.JSONEncoder;
import yongda.rpc.proto.Peer;
import yongda.rpc.transport.client.TransportClient;
import yongda.rpc.transport.client.impl.NettyTransportClient;

import java.util.Collections;
import java.util.List;

/**
 * 客户端配置类
 * @author cdl
 */
@Data
public class RpcClientConfig {

    /**
     * 客户端
     * todo 配置通过application.properties注入
     */
    //private Class<? extends TransportClient> client = HttpTransportClient.class;
    private Class<? extends TransportClient> client = NettyTransportClient.class;

    /**
     * 编码器
     */
    private Class<? extends Encoder> encoder = JSONEncoder.class;

    /**
     * 解码器
     */
    private Class<? extends Decoder> decoder = JSONDecoder.class;

    /**
     * 客户端选择器
     */
    private Class<? extends TransportSelector> selector = RandomTransportSelector.class;

    /**
     * 客户端和服务端的连接数
     */
    private int count = 1;

    /**
     * 可以和客户端建立连接的服务端端点
     */
    private List<Peer> peers = Collections.singletonList(new Peer("127.0.0.1", 3000));
}
