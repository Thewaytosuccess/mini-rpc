package yongda.rpc.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import yongda.rpc.common.ConfigContext;
import yongda.rpc.proto.request.Request;
import yongda.rpc.codec.decoder.Decoder;
import yongda.rpc.codec.encoder.Encoder;
import yongda.rpc.proto.response.Response;
import yongda.rpc.server.config.RpcServerConfig;
import yongda.rpc.proto.registry.ServiceRegistry;
import yongda.rpc.proto.util.ServiceInvoker;
import yongda.rpc.transport.handler.RequestHandler;
import yongda.rpc.transport.server.TransportServer;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.transport.server.impl.HttpTransportServer;
import yongda.rpc.transport.server.impl.NettyTransportServer;

import java.io.IOException;
import java.util.Objects;

/**
 * @author cdl
 */
@Slf4j
public class RpcServer {

    private TransportServer server;

    private RpcServerConfig initRpcServerConfig() {
        RpcServerConfig rpcServerConfig = new RpcServerConfig();
        String transportType ="rpc.transport.type";
        transportType = ConfigContext.get(transportType);

        String http = "http";
        rpcServerConfig.setTransportServer(http.equals(transportType) ?
                HttpTransportServer.class : NettyTransportServer.class);

        String portConfig = "rpc.server.port";
        portConfig = ConfigContext.get(portConfig);
        if(Objects.nonNull(portConfig)){
            rpcServerConfig.setPort(Integer.parseInt(portConfig));
        }
        return rpcServerConfig;
    }

    public RpcServer(){
        RpcServerConfig config = initRpcServerConfig();
        Class<? extends TransportServer> transportServer = config.getTransportServer();
        this.server = ReflectionUtils.newInstance(transportServer);

        if(transportServer == HttpTransportServer.class){
            Encoder encoder = ReflectionUtils.newInstance(config.getEncoder());
            Decoder decoder = ReflectionUtils.newInstance(config.getDecoder());

            RequestHandler handler = (is, os) -> {
                try {
                    //将输入流转化为字节数组
                    //将字节数组反序列化request对象
                    Request request = decoder.decode(IOUtils.readFully(is,
                            is.available()), Request.class);
                    log.info("request: {}",request);

                    //根据request对象查找服务，调用服务，将结果序列化输出
                    Response response = new Response();
                    response.setData(ServiceInvoker.invoke(
                            ServiceRegistry.getInstance().get(request),request));
                    os.write(encoder.encode(response));
                    os.flush();
                } catch (IOException e) {
                    log.error("{}",e);
                }
            };
            this.server.init(config.getPort(), handler);
        }else if(transportServer == NettyTransportServer.class){
            this.server.init(config.getPort(),null);
        }
    }

    public void start(){
        server.start();
        log.info("服务启动成功！");
    }

    public void stop(){
        server.stop();
    }

}
