package yongda.rpc.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import yongda.rpc.proto.Request;
import yongda.rpc.codec.Decoder;
import yongda.rpc.codec.Encoder;
import yongda.rpc.proto.Response;
import yongda.rpc.server.config.RpcServerConfig;
import yongda.rpc.server.registry.ServiceRegistry;
import yongda.rpc.server.service.ServiceInvoker;
import yongda.rpc.transport.handler.RequestHandler;
import yongda.rpc.transport.server.TransportServer;
import yongda.rpc.common.ReflectionUtils;

import java.io.IOException;

@Slf4j
public class RpcServer {

    private Encoder encoder;

    private Decoder decoder;

    private ServiceRegistry registry;

    private ServiceInvoker invoker;

    private TransportServer server;

    public RpcServer(){
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config){
        this.encoder = ReflectionUtils.newInstance(config.getEncoder());
        this.decoder = ReflectionUtils.newInstance(config.getDecoder());
        this.registry = new ServiceRegistry();
        this.invoker = new ServiceInvoker();

        this.server = ReflectionUtils.newInstance(config.getTransportServer());
        RequestHandler handler = (is, os) -> {
            try {
                //将输入流转化为字节数组
                //将字节数组反序列化request对象
                Request request = decoder.decode(IOUtils.readFully(is,
                        is.available()), Request.class);
                log.info("request is null : {}",request == null);

                //根据request对象查找服务，调用服务，将结果序列化输出
                Response response = new Response();
                response.setData(invoker.invoke(registry.get(request),request));
                os.write(encoder.encode(response));
                os.flush();
            } catch (IOException e) {
                log.error("{}",e);
            }
        };
        this.server.init(config.getPort(), handler);
    }

    public <T> T register(Class<T> interfaceClass,T instance){
        log.info("注册服务;{}",interfaceClass.getName());
        return registry.register(interfaceClass,instance);
    }

    public void start(){
        server.start();
        log.info("服务启动成功！");
    }

    public void stop(){
        server.stop();
    }

}
