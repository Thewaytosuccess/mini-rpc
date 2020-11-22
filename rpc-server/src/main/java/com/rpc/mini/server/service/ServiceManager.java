package com.rpc.mini.server.service;

import com.rpc.mini.Request;
import com.rpc.mini.ServiceDescriptor;
import com.rpc.mini.common.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xhzy
 */
@Slf4j
public class ServiceManager {

    private final Map<ServiceDescriptor,ServiceInstance> registry;

    public ServiceManager(){
        registry = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> clazz,T instance){
        ReflectionUtil.getPublicMethods(clazz).forEach(e ->
            registry.put(ServiceDescriptor.of(clazz,e),new ServiceInstance(ReflectionUtil.newInstance(clazz),e))
        );
    }

    public ServiceInstance lookup(Request request){
        return registry.get(request.getServiceDescriptor());
    }
}
