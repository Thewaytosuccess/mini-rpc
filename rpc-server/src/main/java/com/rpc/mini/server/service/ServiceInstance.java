package com.rpc.mini.server.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author xhzy
 */
@Data
@AllArgsConstructor
public class ServiceInstance {

    private Object target;

    private Method method;
}
