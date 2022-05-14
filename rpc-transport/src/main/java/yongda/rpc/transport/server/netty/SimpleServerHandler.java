package yongda.rpc.transport.server.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import yongda.rpc.proto.registry.ServiceRegistry;
import yongda.rpc.proto.request.Request;
import yongda.rpc.proto.response.Response;
import yongda.rpc.proto.util.ServiceInvoker;

/**
 * channel管理和监听
 * @author cdl
 */
@Slf4j
public class SimpleServerHandler extends SimpleChannelInboundHandler<Request> {

    /**
     * 创建channelGroup管理channel
     */
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info("groupName:{}",CHANNEL_GROUP.name());
        for(Channel c:CHANNEL_GROUP){
            c.writeAndFlush(c.remoteAddress() + "加入！\n");
        }
        CHANNEL_GROUP.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        for(Channel c:CHANNEL_GROUP){
            c.writeAndFlush(c.remoteAddress() + "离开！\n");
        }
        CHANNEL_GROUP.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info(channel.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info(channel.remoteAddress() + "离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //获取发生异常的通道
        Channel channel = ctx.channel();
        log.info("ip:" + channel.remoteAddress() + "发生异常！");
        //抛出异常
        cause.printStackTrace();
        //关闭连接
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Request request) {
        log.info("request from server === {}", JSON.toJSONString(request));
        Channel c = ctx.channel();
        //根据request对象查找服务，调用服务，将结果序列化输出
        Response response = new Response();
        response.setRequestId(request.getRequestId());
        response.setData(ServiceInvoker.invoke(ServiceRegistry.getInstance().get(request),request));
        c.writeAndFlush(response);
    }
}
