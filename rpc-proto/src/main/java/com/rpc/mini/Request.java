package com.rpc.mini;

import lombok.Data;

/**
 * @author xhzy
 */
@Data
public class Request {

    private ServiceDescriptor serviceDescriptor;

    private Object[] parameters;
}
