package com.rpc.mini.server.service;

import com.rpc.mini.Request;

/**
 * @author xhzy
 */
public class ServiceInvoker {

    public Object invoke(ServiceInstance service, Request request){
        try {
            return service.getMethod().invoke(service.getTarget(),request.getParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
