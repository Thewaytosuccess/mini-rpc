package com.rpc.mini.service;

/**
 * @author xhzy
 */
public class RpcClient {

    public static void main(String[] args) {
        com.rpc.mini.client.RpcClient client = new com.rpc.mini.client.RpcClient();
        CalculateService service = client.getProxy(CalculateService.class);
        int sum = service.add(1, 2);
        int minus = service.minus(9, 3);
        System.out.println("sum = "+ sum + ";minus = "+minus);
    }
}
