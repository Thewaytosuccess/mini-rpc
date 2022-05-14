package yongda.rpc.client.invoker;

import lombok.extern.slf4j.Slf4j;
import yongda.rpc.client.selector.TransportSelector;
import yongda.rpc.proto.request.Request;
import yongda.rpc.proto.response.Response;
import yongda.rpc.proto.service.ServiceDescriptor;
import yongda.rpc.transport.client.TransportClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author cdl
 * @param <T>
 */
@Slf4j
public class RemoteNettyInvoker<T> implements InvocationHandler {

    private Class<T> clazz;

    /**
     * 客户端选择器：用来选择出一个客户端与服务端建立连接
     */
    private TransportSelector selector;

    public RemoteNettyInvoker(Class<T> clazz,TransportSelector selector) {
        this.clazz = clazz;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //构建request
        Request request = new Request();
        request.setServiceDescriptor(new ServiceDescriptor(clazz,method));
        request.setParameters(args);

        //选择一个服务端点建立连接
        TransportClient client = selector.select();

        //发送请求，获取服务端响应的输入流
        try {
            Response response = client.sendRequest(request);
            int successCode = 200;
            return response.getCode() == successCode ? response.getData() : response;
        } finally {
            selector.release(client);
        }
    }
}
