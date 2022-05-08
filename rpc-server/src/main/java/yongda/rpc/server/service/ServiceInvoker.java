package yongda.rpc.server.service;

import yongda.rpc.proto.Request;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.server.service.ServiceInstance;

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
    public Object invoke(ServiceInstance instance, Request request){
       return ReflectionUtils.invoke(instance.getTarget(),instance.getMethod(),
                request.getParameters());
    }
}
