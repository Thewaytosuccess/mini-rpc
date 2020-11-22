package com.rpc.mini.service;

/**
 * @author xhzy
 */
public class RpcServer {

    public static void main(String[] args) {
        com.rpc.mini.server.RpcServer server = new com.rpc.mini.server.RpcServer();
        server.register(CalculateService.class,new CalculateServiceImpl());
        server.start();
    }
}
