package yongda.rpc.transport.client.netty.context;

import yongda.rpc.proto.response.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class RpcContextHolder {

    private static final Map<String, CompletableFuture<Response>> CONTEXT =
            new ConcurrentHashMap<>();

    public static CompletableFuture<Response> getFuture(String requestId){
        CompletableFuture<Response> future = new CompletableFuture<>();
        CONTEXT.putIfAbsent(requestId,future);
        return future;
    }

    public static void complete(Response response){
        CONTEXT.remove(response.getRequestId()).complete(response);
    }
}
