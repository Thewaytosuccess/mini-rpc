package yongda.rpc.client;

import yongda.rpc.client.config.RpcClientConfig;
import yongda.rpc.client.invoker.RemoteHttpInvoker;
import yongda.rpc.client.invoker.RemoteNettyInvoker;
import yongda.rpc.client.selector.TransportSelector;
import yongda.rpc.codec.decoder.Decoder;
import yongda.rpc.codec.encoder.Encoder;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.transport.client.impl.HttpTransportClient;
import yongda.rpc.transport.client.impl.NettyTransportClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * rpc客户端
 * @author cdl
 */
public class RpcClient {

    private RpcClientConfig config;

    private TransportSelector selector;

    public RpcClient(){
        this(new RpcClientConfig());
    }

    private RpcClient(RpcClientConfig config){
        this.config = config;
        this.selector = ReflectionUtils.newInstance(config.getSelector());
        this.selector.init(config.getPeers(),config.getCount(),config.getClient());
    }

    /**
     * 动态代理，通过接口来调用具体的实现类
     * @param clazz 接口类
     * @param <T> 接口实例
     * @return 接口实例
     */
    public <T> T getProxy(Class<T> clazz){
        InvocationHandler handler;

        if(this.config.getClient() == HttpTransportClient.class){
            Encoder encoder = ReflectionUtils.newInstance(config.getEncoder());
            Decoder decoder = ReflectionUtils.newInstance(config.getDecoder());
            handler = new RemoteHttpInvoker<>(clazz,encoder, decoder,selector);
        }else{
            handler = new RemoteNettyInvoker<>(clazz,selector);
        }

        return (T)Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{clazz},handler);
    }
}
