package yongda.rpc.client.invoker;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import yongda.rpc.client.selector.TransportSelector;
import yongda.rpc.codec.decoder.Decoder;
import yongda.rpc.codec.encoder.Encoder;
import yongda.rpc.proto.Request;
import yongda.rpc.proto.Response;
import yongda.rpc.proto.ServiceDescriptor;
import yongda.rpc.transport.client.TransportClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 利用动态代理实现接口实例的调用
 * @param <T>
 * @author cdl
 */
@Slf4j
public class RemoteHttpInvoker<T> implements InvocationHandler {

    /**
     * 编码器：用来将客户端请求参数序列化
     */
    private Encoder encoder;

    /**
     * 解码器：用来将服务端响应反序列化
     */
    private Decoder decoder;

    /**
     * 客户端选择器：用来选择出一个客户端与服务端建立连接
     */
    private TransportSelector selector;

    /**
     * 接口类：用来构造出调用的服务
     */
    private Class<T> clazz;

    public RemoteHttpInvoker(Class<T> clazz, Encoder encoder, Decoder decoder,
                             TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //构建request
        Request request = new Request();
        request.setServiceDescriptor(new ServiceDescriptor(clazz,method));
        request.setParameters(args);

        //将request序列化
        byte[] data = encoder.encode(request);
        //选择一个服务端点建立连接
        TransportClient client = selector.select();

        //发送请求，获取服务端响应的输入流
        try (InputStream is = client.sendRequest(new ByteArrayInputStream(data))){
             //将结果反序列化为response对象
             return decoder.decode(IOUtils.readFully(is,is.available()), Response.class)
                     .getData();
        } catch (IOException e) {
            log.error("{}",e);
            Response response = new Response();
            response.setCode(0);
            response.setMessage(e.getMessage());
            return response;
        } finally {
            selector.release(client);
        }
    }

}
