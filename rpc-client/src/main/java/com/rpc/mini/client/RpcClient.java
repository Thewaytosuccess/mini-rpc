package com.rpc.mini.client;

import com.rpc.mini.client.config.RpcClientConfig;
import com.rpc.mini.client.proxy.RpcInvocationHandler;
import com.rpc.mini.client.selector.TransportClientSelector;
import com.rpc.mini.codec.Decoder;
import com.rpc.mini.codec.Encoder;
import com.rpc.mini.common.util.ReflectionUtil;

import java.lang.reflect.Proxy;

/**
 * @author xhzy
 */
public class RpcClient {

    private Encoder encoder;

    private Decoder decoder;

    private TransportClientSelector selector;

    public RpcClient(){
       new RpcClient(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config){
        this.encoder = ReflectionUtil.newInstance(config.getEncoder());
        this.decoder = ReflectionUtil.newInstance(config.getDecoder());

        this.selector = ReflectionUtil.newInstance(config.getSelector());
        selector.init(config.getServers(),config.getConnectedCount(),config.getClient());

    }

    public <T> T getProxy(Class<T> clazz){
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),new RpcInvocationHandler(
                clazz,encoder,decoder,selector));
    }
}
