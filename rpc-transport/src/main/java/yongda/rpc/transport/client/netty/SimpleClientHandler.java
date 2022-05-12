package yongda.rpc.transport.client.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import yongda.rpc.proto.Response;

/**
 * 客户端处理器
 * @author cdl
 */
@Slf4j
public class SimpleClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) {
        log.info("获取到服务端响应===={}",s);
        RpcContextHolder.complete(JSON.parseObject(s, Response.class));
    }
}
