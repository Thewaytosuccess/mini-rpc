package yongda.rpc.transport.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 客户端初始化器，用来设置初始化参数
 */
public class SimpleClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //管道线
        ChannelPipeline pipe = ch.pipeline();

        //分隔符
        pipe.addLast("framer",new DelimiterBasedFrameDecoder(8192,
                Delimiters.lineDelimiter()));

        //编码器
        pipe.addLast("encoder",new StringEncoder());

        //解码器
        pipe.addLast("decoder",new StringDecoder());

        //处理器
        pipe.addLast("handler",new SimpleClientHandler());
    }
}
