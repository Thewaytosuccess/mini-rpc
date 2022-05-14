package yongda.rpc.transport.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import yongda.rpc.codec.decoder.impl.NettyServerDecoder;
import yongda.rpc.codec.encoder.impl.NettyClientEncoder;
import yongda.rpc.codec.encoder.impl.NettyServerEncoder;

/**
 * 服务端初始化类
 * @author cdl
 */
public class SimpleServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        //管道线
        ChannelPipeline pipe = channel.pipeline();

        //分隔符
//        pipe.addLast("framer", new DelimiterBasedFrameDecoder(8192,
//                Delimiters.lineDelimiter()));

        //解码器
//        pipe.addLast("decoder",new StringDecoder());
        pipe.addLast("decoder",new NettyServerDecoder());

        //编码器
//        pipe.addLast("encoder", new StringEncoder());
        pipe.addLast("encoder",new NettyServerEncoder());

        //处理器
        pipe.addLast("handler",new SimpleServerHandler());

        System.out.println("ip:" + channel.remoteAddress() + "建立连接");
    }

}
