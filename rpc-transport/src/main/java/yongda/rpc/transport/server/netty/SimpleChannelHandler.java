package yongda.rpc.transport.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * channel管理和监听
 * @author cdl
 */
public class SimpleChannelHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 创建channelGroup管理channel
     */
    private static ChannelGroup group = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("groupName:" + group.name());
        for(Channel c:group){
            c.writeAndFlush(c.remoteAddress() + "加入！\n");
        }
        group.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for(Channel c:group){
            c.writeAndFlush(c.remoteAddress() + "离开！\n");
        }
        group.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //获取发生异常的通道
        Channel channel = ctx.channel();
        System.out.println("ip:" + channel.remoteAddress() + "发生异常！");
        //抛出异常
        cause.printStackTrace();
        //关闭连接
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String s) throws Exception {
        Channel c = ctx.channel();
        for(Channel ch:group){
            if(ch != c){
                ch.writeAndFlush("【" + ch.remoteAddress() + "】" + s + "\n");
            }else{
                ch.writeAndFlush("【you】" + s + "\n");
            }
        }
    }
}
