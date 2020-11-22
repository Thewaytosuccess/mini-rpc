package com.rpc.mini.client.proxy;

import com.rpc.mini.Request;
import com.rpc.mini.Response;
import com.rpc.mini.ServiceDescriptor;
import com.rpc.mini.client.selector.TransportClientSelector;
import com.rpc.mini.codec.Decoder;
import com.rpc.mini.codec.Encoder;
import com.rpc.mini.transport.client.TransportClient;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author xhzy
 */
public class RpcInvocationHandler implements InvocationHandler {

    private final Encoder encoder;

    private final Decoder decoder;

    private final TransportClientSelector selector;

    private final Class<?> clazz;

    public RpcInvocationHandler(Class<?> clazz,Encoder encoder, Decoder decoder, TransportClientSelector selector){
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //1.构造请求对象
        Request request = new Request();
        request.setServiceDescriptor(ServiceDescriptor.of(clazz,method));
        request.setParameters(args);

        //2.选择一个客户端发送请求
        TransportClient client = null;
        try {
            client = selector.select();
            InputStream inputStream = client.write(new ByteArrayInputStream(encoder.encode(request)));
            Response response = decoder.decode(IOUtils.readFully(inputStream, inputStream.available()), Response.class);
            int expected = 200;
            if(Objects.nonNull(response)){
                if(response.getCode() == expected){
                    return response.getData();
                }else{
                    throw new RuntimeException(response.getCode()+":"+response.getMessage());
                }
            }else{
                throw new RuntimeException("rpc invoke failed,response is null");
            }
        } finally {
            if(Objects.nonNull(client)){
                selector.release(client);
            }
        }
    }
}
