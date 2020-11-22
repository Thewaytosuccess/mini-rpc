package com.rpc.mini;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务描述
 * @author xhzy
 */
@Data
@EqualsAndHashCode
public class ServiceDescriptor {

    private String clazz;

    private String method;

    private String returnType;

    private List<String> parameterTypes;

    public static ServiceDescriptor of(Class<?> clazz, Method method){
        ServiceDescriptor descriptor = new ServiceDescriptor();
        descriptor.setClazz(clazz.getName());
        descriptor.setMethod(method.getName());
        descriptor.setReturnType(method.getReturnType().getName());
        descriptor.setParameterTypes(Arrays.stream(method.getParameterTypes()).map(Class::getName).
                collect(Collectors.toList()));
        return descriptor;
    }
}
