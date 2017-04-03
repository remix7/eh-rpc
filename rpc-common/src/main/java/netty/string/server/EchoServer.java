package netty.string.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * -------------------------------------
 * TODO
 * -------------------------------------
 * Created by liutao on 2017/4/3 下午5:12.
 */
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = null;
        try {
            //server 端引导类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //连接池处理数据
            group = new NioEventLoopGroup();
            //装配bootStrap
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress("localhost", port)
                    .childHandler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new EchoServerHandler());//注册handler
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind().sync();
            System.out.println("开始监听：" + channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(9999).start();
    }
}
