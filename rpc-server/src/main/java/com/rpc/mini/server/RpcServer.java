package com.rpc.mini.server;

import com.rpc.mini.Request;
import com.rpc.mini.Response;
import com.rpc.mini.codec.Decoder;
import com.rpc.mini.codec.Encoder;
import com.rpc.mini.common.util.ReflectionUtil;
import com.rpc.mini.server.config.RpcServerConfig;
import com.rpc.mini.server.service.ServiceInvoker;
import com.rpc.mini.server.service.ServiceManager;
import com.rpc.mini.transport.server.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * @author xhzy
 */
@Slf4j
public class RpcServer {

    private TransportServer server;

    private ServiceManager serviceManager;

    public RpcServer(){
        new RpcServer(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config){
        Encoder encoder = ReflectionUtil.newInstance(config.getEncoder());
        Decoder decoder = ReflectionUtil.newInstance(config.getDecoder());
        this.server = ReflectionUtil.newInstance(config.getTransportServer());
        this.serviceManager = new ServiceManager();
        ServiceInvoker invoker = new ServiceInvoker();

        this.server.init(config.getPort(), (inputStream, outputStream) -> {
            Response<Object> response = null;
            try{
                //读取输入流,将输入流序列化成request对象
                Request request = decoder.decode(IOUtils.readFully(inputStream, inputStream.available()), Request.class);
                //利用request对象查找对应的服务并调用
                response = Response.success(invoker.invoke(serviceManager.lookup(request),request));
            } catch (IOException e) {
                log.error(e.toString());
                response = Response.fail(e.getClass().getName());
            } finally {
                //将结果序列化返回
                try {
                    outputStream.write(encoder.encode(response));
                } catch (IOException e) {
                    log.error(e.toString());
                }
            }
        });
    }

    public void start(){
        this.server.start();
    }

    public void stop(){
        this.server.stop();
    }

    public <T> void register(Class<T> clazz,T instance){
        this.serviceManager.register(clazz,instance);
    }

}
