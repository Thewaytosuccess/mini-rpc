package yongda.rpc.server.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author cdl
 */
@Data
@AllArgsConstructor
public class ServiceInstance {

    /**
     * 被调用类的实例对象
     */
    private Object target;

    /**
     * 被调用的方法
     */
    private Method method;
}
