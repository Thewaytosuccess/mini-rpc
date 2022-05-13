package yongda.rpc.transport.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import yongda.rpc.transport.handler.RequestHandler;
import yongda.rpc.transport.server.TransportServer;
import yongda.rpc.transport.server.netty.SimpleServerInitializer;

@Slf4j
public class NettyTransportServer implements TransportServer {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private int port;

    @Override
    public void init(int port, RequestHandler handler) {
        this.port = port;
    }


    @SneakyThrows
    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        //绑定主从
        server.group(bossGroup, workGroup)
                //服务器通道
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleServerInitializer())
                //配置项
                .option(ChannelOption.SO_BACKLOG, 128)
                //设置长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        //绑定端口，接收连接
        ChannelFuture channelFuture = server.bind(port).sync();
        //关闭
        channelFuture.channel().closeFuture().sync();
    }

    @Override
    public void stop() {
        //优雅关闭
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
