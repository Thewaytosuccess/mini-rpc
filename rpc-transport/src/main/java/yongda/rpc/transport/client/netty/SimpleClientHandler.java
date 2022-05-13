package yongda.rpc.transport.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import yongda.rpc.proto.response.Response;

/**
 * 客户端处理器
 * @author cdl
 */
@Slf4j
public class SimpleClientHandler extends SimpleChannelInboundHandler<Response> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext,
                                   Response response) {
        log.info("获取到服务端响应===={}",response);
        RpcContextHolder.complete(response);
    }
}
