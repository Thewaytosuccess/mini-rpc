package yongda.rpc.transport.client.impl;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import yongda.rpc.proto.Peer;
import yongda.rpc.proto.request.Request;
import yongda.rpc.proto.response.Response;
import yongda.rpc.transport.client.TransportClient;
import yongda.rpc.transport.client.netty.context.RpcContextHolder;
import yongda.rpc.transport.client.netty.SimpleClientInitializer;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author cdl
 */
@Slf4j
public class NettyTransportClient implements TransportClient {

    private EventLoopGroup worker;

    private Channel channel;

    @SneakyThrows
    @Override
    public void connect(Peer peer) {
        worker = new NioEventLoopGroup();

        Bootstrap boot = new Bootstrap()
                .group(worker)
                //客户端通道
                .channel(NioSocketChannel.class)
                //注册客户端初始化器
                .handler(new SimpleClientInitializer());

        //连接服务器
        channel = boot.connect(peer.getHost(), peer.getPort()).sync().channel();
    }

    @SneakyThrows
    @Override
    public Response sendRequest(Request request) {
        String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);

        //预相应
        CompletableFuture<Response> promise = RpcContextHolder.getFuture(requestId);

        log.info("request ============== {}", JSON.toJSONString(request));
        //send request
        channel.writeAndFlush(request).addListener(e -> {
            if(e.isSuccess()){
                log.info("send request success =================");
            }
        });

        //阻塞，以获取结果
        long timeout = 100;
        return promise.get(timeout, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        if(Objects.nonNull(worker)){
            worker.shutdownGracefully();
        }
    }
}
