package yongda.rpc.transport.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import yongda.rpc.codec.decoder.impl.NettyClientDecoder;
import yongda.rpc.codec.encoder.impl.NettyClientEncoder;

/**
 * 客户端初始化器，用来设置初始化参数
 * @author cdl
 */
public class SimpleClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        //管道线
        ChannelPipeline pipe = ch.pipeline();

        //分隔符
//        pipe.addLast("framer",new DelimiterBasedFrameDecoder(8192,
//                Delimiters.lineDelimiter()));

        //编码器
        //pipe.addLast("encoder",new StringEncoder());
        pipe.addLast("encoder",new NettyClientEncoder());

        //解码器
        //pipe.addLast("decoder",new StringDecoder());
        pipe.addLast("decoder",new NettyClientDecoder());

        //处理器
        pipe.addLast("handler",new SimpleClientHandler());
    }
}
