package cn.echoes.rpc.client;

import cn.echoes.rpc.common.RpcRequest;
import cn.echoes.rpc.common.RpcResponse;
import cn.echoes.rpc.registry.ServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * -------------------------------------
 * rpc 代理对象
 * -------------------------------------
 * Created by liutao on 2017/3/30 下午12:56.
 */
public class RpcProxy {
    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    /**
     * 创建代理对象
     *
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //创建RpcRequest 封装被代理的对象
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setRequestId(UUID.randomUUID().toString());
                //拿到方法声明的接口
                rpcRequest.setClassName(method.getDeclaringClass().getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                //查找服务
                if (serviceDiscovery != null) {
                    serverAddress = serviceDiscovery.discover();
                }
                //获取随机地址
                String[] array = serverAddress.split(":");
                String host = array[0];
                int port = Integer.parseInt(array[1]);
                //创建netty 实现的RpcClient 连接服务器
                RpcClient rpcClient = new RpcClient(host, port);
                RpcResponse rpcResponse = rpcClient.send(rpcRequest);
                if (rpcResponse.isError()) {
                    throw rpcResponse.getError();
                } else {
                    return rpcResponse.getResult();
                }
            }
        });
    }
}
