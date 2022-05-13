package yongda.rpc.proto.util;

import yongda.rpc.proto.request.Request;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.proto.service.ServiceInstance;

/**
 * @author cdl
 */
public class ServiceInvoker {

    /**
     * 调用服务
     * @param instance 实例
     * @param request 请求参数
     * @return 执行结果
     */
    public static Object invoke(ServiceInstance instance, Request request){
       return ReflectionUtils.invoke(instance.getTarget(),instance.getMethod(),
                request.getParameters());
    }
}
