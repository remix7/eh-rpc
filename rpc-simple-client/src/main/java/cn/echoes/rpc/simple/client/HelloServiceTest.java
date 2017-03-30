package cn.echoes.rpc.simple.client;

import cn.echoes.rpc.base.HelloService;
import cn.echoes.rpc.base.Person;
import cn.echoes.rpc.client.RpcProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * -------------------------------------
 * 测试程序
 * -------------------------------------
 * Created by liutao on 2017/3/30 下午1:16.
 */
public class HelloServiceTest {
    @Autowired
    private RpcProxy rpcProxy;

    public void testString() {
        HelloService service = rpcProxy.create(HelloService.class);
        String result = service.hello("HELLO");
        System.out.printf(result);
    }

    public void testBean() {
        HelloService service = rpcProxy.create(HelloService.class);
        String result = service.hello(new Person("what", "fuck"));
        System.out.printf(result);
    }

}
