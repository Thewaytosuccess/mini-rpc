package yongda.rpc.transport.client.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import yongda.rpc.proto.Peer;
import yongda.rpc.transport.client.TransportClient;
import yongda.rpc.transport.client.netty.SimpleClientInitializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

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
    public InputStream sendRequest(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        while(true){
            channel.writeAndFlush(reader.readLine() + "\r\n");
        }
    }

    @Override
    public void close() {
        if(Objects.nonNull(worker)){
            worker.shutdownGracefully();
        }
    }
}
