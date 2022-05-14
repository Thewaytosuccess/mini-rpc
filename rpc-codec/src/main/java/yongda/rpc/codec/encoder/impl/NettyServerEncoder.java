package yongda.rpc.codec.encoder.impl;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import yongda.rpc.proto.response.Response;

/**
 * @author cdl
 *
 * 常用的协议制定方法：
 * 指定分隔符；
 * 报文定长；
 * 报文长度+内容；
 */
public class NettyServerEncoder extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          Response response, ByteBuf byteBuf) {
        //定义协议：报文长度 + 报文内容
        byte[] bytes = JSON.toJSONBytes(response);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
