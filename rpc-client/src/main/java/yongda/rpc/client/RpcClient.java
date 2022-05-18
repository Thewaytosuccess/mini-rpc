package yongda.rpc.client;

import lombok.extern.slf4j.Slf4j;
import yongda.rpc.client.config.RpcClientConfig;
import yongda.rpc.client.invoker.RemoteHttpInvoker;
import yongda.rpc.client.invoker.RemoteNettyInvoker;
import yongda.rpc.client.selector.TransportSelector;
import yongda.rpc.codec.decoder.Decoder;
import yongda.rpc.codec.encoder.Encoder;
import yongda.rpc.common.ConfigContext;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.proto.Peer;
import yongda.rpc.proto.registry.ServiceRegistry;
import yongda.rpc.transport.client.impl.HttpTransportClient;
import yongda.rpc.transport.client.impl.NettyTransportClient;
import yongda.rpc.transport.server.impl.HttpTransportServer;
import yongda.rpc.transport.server.impl.NettyTransportServer;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * rpc客户端
 * @author cdl
 */
@Slf4j
public class RpcClient {

    private RpcClientConfig config;

    private TransportSelector selector;

    private static RpcClient client = new RpcClient();

    private static RpcClient getInstance(){
        return client;
    }

    private RpcClient(){
        this.config = initRpcClientConfig();
        this.selector = ReflectionUtils.newInstance(config.getSelector());
        this.selector.init(config.getPeers(),config.getCount(),config.getClient());
    }

    private RpcClientConfig initRpcClientConfig(){
        RpcClientConfig rpcClientConfig = new RpcClientConfig();
        String transportType ="rpc.transport.type";
        transportType = ConfigContext.get(transportType);

        String http = "http";
        rpcClientConfig.setClient(http.equals(transportType) ?
                HttpTransportClient.class : NettyTransportClient.class);

        String host = "rpc.server.host";
        String port = "rpc.server.port";
        host = ConfigContext.get(host);
        port = ConfigContext.get(port);

        if(Objects.nonNull(host) && Objects.nonNull(port)){
            rpcClientConfig.setPeers(Collections.singletonList(
                    new Peer(host,Integer.valueOf(port))));
        }
        return rpcClientConfig;
    }

    public static <T> T getService(Class<T> clazz){
        String servicePackageConfig = "rpc.component.scan.package";
        ServiceRegistry.registerService(servicePackageConfig, RpcClient::autowire);
        return (T)ServiceRegistry.getInstance().getObject(clazz);
    }

    private static void autowire(Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredFields())
            .filter(e -> e.isAnnotationPresent(Resource.class))
            .forEach(e -> {
                //setter
                String field = e.getName();
                String setter = "set" + field.substring(0,1).toUpperCase()
                        + field.substring(1);
                try {
                    clazz.getDeclaredMethod(setter,e.getType()).invoke(
                            ServiceRegistry.getInstance().getObject(clazz),
                            getProxy(e.getType()));
                } catch (Exception ex) {
                    log.error(ex.getMessage(),ex);
                }
            });
    }

    /**
     * 动态代理，通过接口来调用具体的实现类
     * @param clazz 接口类
     * @param <T> 接口实例
     * @return 接口实例
     */
    private static <T> T getProxy(Class<T> clazz){
        InvocationHandler handler;

        RpcClientConfig config = getInstance().config;
        TransportSelector selector = getInstance().selector;

        if(config.getClient() == HttpTransportClient.class){
            Encoder encoder = ReflectionUtils.newInstance(config.getEncoder());
            Decoder decoder = ReflectionUtils.newInstance(config.getDecoder());
            handler = new RemoteHttpInvoker<>(clazz, encoder, decoder, selector);
        }else{
            handler = new RemoteNettyInvoker<>(clazz,selector);
        }

        return (T)Proxy.newProxyInstance(RpcClient.class.getClassLoader(),
                new Class[]{clazz},handler);
    }
}
