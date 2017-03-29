package cn.echoes.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * -------------------------------------
 * 编码
 * -------------------------------------
 * Created by liutao on 2017/3/30 0:08.
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> genericClass;

    // 构造函数传入向反序列化的class
    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object inob, ByteBuf out)
            throws Exception {
        //序列化
        if (genericClass.isInstance(inob)) {
            byte[] data = SerializationUtil.serialize(inob);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
