package netty.string.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * -------------------------------------
 * TODO
 * -------------------------------------
 * Created by liutao on 2017/4/3 下午5:26.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接服务端准备发送数据。。");
        byte[] b = "QUERY TIME ORDER".getBytes();
        ByteBuf byteBuf = Unpooled.buffer(b.length);
        byteBuf.writeBytes(b);
        ctx.writeAndFlush(byteBuf);
    }

    //  从服务器获取到数据时调用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client read server data...");
        ByteBuf byteBuf = msg;
        byte[] b = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(b);
        String body = new String(b, "UTF-8");
        System.out.println("读取到的数据为：" + body);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
