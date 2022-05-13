package yongda.rpc.proto.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 服务描述/定义
 * @author cdl
 */
@Data
@NoArgsConstructor
public class ServiceDescriptor {

    /**
     * 接口类
     */
    private String clazz;

    /**
     * 方法
     */
    private String method;

    /**
     * 返回值类型
     */
    private String returnType;

    /**
     * 参数类型
     */
    private String[] parameterTypes;

    public ServiceDescriptor(Class clazz, Method method){
        this.clazz = clazz.getName();
        this.method = method.getName();
        this.returnType = method.getReturnType().getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        if(this.parameterTypes == null){
            this.parameterTypes = new String[parameterTypes.length];
        }
        for(int i = 0,len = parameterTypes.length; i < len ; i++){
            this.parameterTypes[i] = parameterTypes[i].getName();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDescriptor that = (ServiceDescriptor) o;
        return this.toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "ServiceDescriptor{" +
                "clazz='" + clazz + '\'' +
                ", method='" + method + '\'' +
                ", returnType='" + returnType + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                '}';
    }
}
