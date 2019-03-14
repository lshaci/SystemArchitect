package com.lshaci.learning.netty.stick1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("Channel active.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 因为添加了StringDecoder，所有接收到的msg为String类型
            String req = (String) msg;
            System.err.println("Server: " + req);
            // 写响应给客户端
            ctx.channel()
                    .writeAndFlush(Unpooled.copiedBuffer("这是响应$_".getBytes()));
            ;
        } finally {
            // 有写操作的时候会自动释放msg
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
