package yongda.rpc.codec.decoder.impl;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import yongda.rpc.proto.response.Response;

import java.util.List;

/**
 * netty解码器
 * @author cdl
 */
public class NettyClientDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) {

        //因为报文=报文长度+内容，而长度为int类型，至少占4个字节
        int minLength = 4;
        if(byteBuf.readableBytes() < minLength){
           return;
        }

        //标记起始位置
        byteBuf.markReaderIndex();

        //读取报文长度
        int length = byteBuf.readInt();
        if(length <= 0){
            channelHandlerContext.close();
            return;
        }

        //报文不完整，有丢失
        if(byteBuf.readableBytes() < length){
            byteBuf.resetReaderIndex();
            return;
        }

        //读取报文内容
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        list.add(JSON.parseObject(bytes, Response.class));
    }
}
