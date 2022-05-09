package yongda.rpc.transport.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 服务端初始化类
 * @author cdl
 */
public class SimpleServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //管道线
        ChannelPipeline pipe = channel.pipeline();

        //分隔符
        pipe.addLast("framer", new DelimiterBasedFrameDecoder(8192,
                Delimiters.lineDelimiter()));

        //解码器
        pipe.addLast("decoder",new StringDecoder());

        //编码器
        pipe.addLast("encoder", new StringEncoder());

        //处理器
        pipe.addLast("handler",new SimpleChannelHandler());

        System.out.println("ip:"+channel.remoteAddress()+"建立连接");
    }

}
