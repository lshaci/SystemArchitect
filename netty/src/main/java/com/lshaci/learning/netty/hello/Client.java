package com.lshaci.learning.netty.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static void main(String[] args) throws Exception {
        //2 第二个线程组 是用于实际的业务处理操作的
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //3 创建一个辅助类Bootstrap，就是对我们的Server进行一系列的配置
        Bootstrap b = new Bootstrap();
        //把俩个工作线程组加入进来
        ChannelFuture f = b.group(workerGroup)
                //我要指定使用NioSocketChannel这种类型的通道
                .channel(NioSocketChannel.class)
                //一定要使用 handler 去绑定具体的 事件处理器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientHandler());
                    }
                })
                // 绑定指定的端口 进行监听
                .connect("127.0.0.1", 8765)
                .sync();

        //buf
        f.channel().writeAndFlush(Unpooled.copiedBuffer("你好".getBytes()));
        //Thread.sleep(1000000);
        f.channel().closeFuture().sync();

        workerGroup.shutdownGracefully();
    }
}
