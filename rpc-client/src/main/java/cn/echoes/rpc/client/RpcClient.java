package cn.echoes.rpc.client;

import cn.echoes.rpc.common.RpcDecoder;
import cn.echoes.rpc.common.RpcEncoder;
import cn.echoes.rpc.common.RpcRequest;
import cn.echoes.rpc.common.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * -------------------------------------
 * rpc 框架客户端
 * -------------------------------------
 * Created by liutao on 2017/3/30 下午12:44.
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);
    private String host;
    private int port;

    private RpcResponse rpcResponse;
    private final Object obj = new Object();

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 连接服务端 发送消息
     *
     * @param rpcRequest
     * @return
     */
    public RpcResponse send(RpcRequest rpcRequest) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline()
                                    .addLast(new RpcEncoder(RpcRequest.class)) //out
                                    .addLast(new RpcDecoder(RpcResponse.class)) //in
                                    .addLast(RpcClient.this); //in
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().writeAndFlush(rpcRequest).sync();
            /**
             * 用线程等待的方式决定是否关闭连接
             * 意义是：先将其阻塞 获取服务器的返回后 被唤醒 从而关闭连接
             */
            synchronized (obj) {
                obj.wait();
            }
            if (rpcResponse == null) {
                future.channel().closeFuture().sync();
            }
        } finally {
            group.shutdownGracefully();
        }
        return null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        this.rpcResponse = rpcResponse;
        synchronized (obj) {
            obj.notify();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
