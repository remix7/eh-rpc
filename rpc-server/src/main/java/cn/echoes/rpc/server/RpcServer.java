package cn.echoes.rpc.server;

import cn.echoes.rpc.common.RpcDecoder;
import cn.echoes.rpc.common.RpcEncoder;
import cn.echoes.rpc.common.RpcRequest;
import cn.echoes.rpc.common.RpcResponse;
import cn.echoes.rpc.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * -------------------------------------
 * RPC SERVER
 * -------------------------------------
 * Created by liutao on 2017/3/30 0:12.
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private String serverAddress;
    private ServiceRegistry serviceRegistry;

    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        handlerMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isEmpty(handlerMap)) {
            for (Object serviceBean : handlerMap.values()) {
                String intrefaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(intrefaceName, serviceBean);
            }
        }
    }

    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(new RpcHandler(handlerMap));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
        } catch (Exception e) {

        }

    }
}
