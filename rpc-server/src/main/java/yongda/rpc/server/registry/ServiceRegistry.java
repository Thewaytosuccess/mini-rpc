package yongda.rpc.server.registry;

import yongda.rpc.proto.Request;
import yongda.rpc.proto.ServiceDescriptor;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.server.service.ServiceInstance;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心
 * @author cdl
 */
public class ServiceRegistry {

    private Map<ServiceDescriptor, ServiceInstance> registry;

    public ServiceRegistry(){
        this.registry = new ConcurrentHashMap<>();
    }

    /**
     * 将接口的所有方法都注册到注册中心
     * @param interfaceClass 接口
     * @param instance 实例
     * @param <T> 实例类型
     * @return 实例
     */
    public <T> T register(Class<T> interfaceClass,T instance){
        //根据接口获取所有的公共方法
        Method[] publicMethods = ReflectionUtils.getPublicMethods(interfaceClass);

        //将所有的方法都注册的注册中心
        Arrays.stream(publicMethods).forEach(e -> registry.put(
                new ServiceDescriptor(interfaceClass,e),
                new ServiceInstance(instance,e)));
        return instance;
    }

    /**
     * 查找服务
     * @param request 请求
     * @return 服务实例
     */
    public ServiceInstance get(Request request){
        return registry.get(request.getServiceDescriptor());
    }
}
